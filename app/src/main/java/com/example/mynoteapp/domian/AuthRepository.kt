package com.example.mynoteapp.domian
import com.example.mynoteapp.utils.Response
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow


interface AuthRepository{
    fun isUserAuthenticatedInFirebase() : Flow<Boolean>
    suspend fun oneTapSignInWithGoogle() : Response<BeginSignInResult>
    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential) : Response<Boolean>

}