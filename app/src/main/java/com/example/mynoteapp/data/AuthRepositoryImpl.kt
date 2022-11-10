package com.example.mynoteapp.data


import android.util.Log
import com.example.mynoteapp.domian.AuthRepository
import com.example.mynoteapp.utils.Constants
import com.example.mynoteapp.utils.Constants.APP_TAG
import com.example.mynoteapp.utils.Constants.CREATED_AT
import com.example.mynoteapp.utils.Constants.DISPLAY_NAME
import com.example.mynoteapp.utils.Constants.EMAIL
import com.example.mynoteapp.utils.Constants.PHOTO_URL
import com.example.mynoteapp.utils.Constants.SIGN_IN_REQUEST
import com.example.mynoteapp.utils.Constants.SIGN_UP_REQUEST
import com.example.mynoteapp.utils.Constants.USERS
import com.example.mynoteapp.utils.Response
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor(
    @Named(SIGN_IN_REQUEST)
    val signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    val signUpRequest: BeginSignInRequest,
    private val oneTapClient: SignInClient,
    private val fbAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthRepository {

    override var isUserAuthenticatedInFirebase: Flow<Boolean> =
        flow { emit(fbAuth.currentUser != null) }.flowOn(Dispatchers.IO)


    override suspend fun oneTapSignInWithGoogle(): Response<BeginSignInResult> {

        return try {
            Response.Loading
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()

            Response.Success(signInResult)
        } catch (e: Exception) {
            try {
                Response.Loading
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Response.Success(signUpResult)
            } catch (e: Exception) {
                Response.Failure(e)
            }
        }
    }


    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): Response<Boolean> {
        return try {
            val authResult = fbAuth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            Log.e(APP_TAG, "isNewUser- $isNewUser" )
            /*if (isNewUser) {}*/
            addUserToFireStore()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    private suspend fun addUserToFireStore() {
        fbAuth.currentUser?.apply {
            val user = toUser()
            db.collection(USERS).document(uid).set(user)
                .addOnSuccessListener {
                Log.e(APP_TAG, "userCreated- Success" )

            }.addOnFailureListener {
                Log.e(APP_TAG, "userCreated- ${it.printStackTrace()}" )
            }.await()
        }
    }


    private fun FirebaseUser.toUser() = mapOf(
        DISPLAY_NAME to displayName,
        EMAIL to email,
        PHOTO_URL to photoUrl?.toString(),
        CREATED_AT to FieldValue.serverTimestamp()
    )

}