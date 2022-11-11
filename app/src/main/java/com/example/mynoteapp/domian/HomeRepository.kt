package com.example.mynoteapp.domian

import com.example.mynoteapp.utils.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

   suspend fun listNotes() : Flow<Response<List<DocumentSnapshot>>>

}