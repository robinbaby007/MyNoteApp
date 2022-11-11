package com.example.mynoteapp.data

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import com.example.mynoteapp.domian.HomeRepository
import com.example.mynoteapp.utils.Constants
import com.example.mynoteapp.utils.Constants.NOTES
import com.example.mynoteapp.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore, private val firebaseAuth: FirebaseAuth
) : HomeRepository {
    override suspend fun listNotes(): Flow<Response<List<DocumentSnapshot>>> {
        val uId = firebaseAuth.currentUser?.uid
        val noteList = mutableListOf<DocumentSnapshot>()

        return flow {
            emit(Response.Loading)
            val response =
                fireStore.collection(Constants.USERS).document(uId ?: "").collection(NOTES).get()
                    .also {
                        it.await()
                    }
            if (response.isComplete) {
                Log.e(Constants.APP_TAG, "docSize- ${response.result.documents.size}")
                response.result.documents.forEach {
                    noteList.add(it)
                }
                emit(Response.Success(noteList))
            } else {
                emit(Response.Failure(response.exception.toString()))
                Log.e(Constants.APP_TAG, "docReadException- ${response.exception}")
            }


            /*.addOnSuccessListener { document ->
                try {
                    Log.e(Constants.APP_TAG, "docSize- ${document.documents.size}")
                    for (doc in document.documents) {
                        noteList.add(doc)
                    }
                } catch (ex: Exception) {
                    Log.e(Constants.APP_TAG, "docReadException- ${ex.printStackTrace()}")
                }
            }.addOnFailureListener { ex ->
                Log.e(Constants.APP_TAG, "docReadException- ${ex.printStackTrace()}")

            }*/
        }

    }


}