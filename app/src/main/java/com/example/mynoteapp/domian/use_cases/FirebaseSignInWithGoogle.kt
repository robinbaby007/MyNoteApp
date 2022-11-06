package com.example.mynoteapp.domian.use_cases

import com.example.mynoteapp.domian.AuthRepository
import com.example.mynoteapp.utils.Response
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class FirebaseSignInWithGoogle  @Inject constructor(private val authRepository: AuthRepository)  {

    suspend operator fun invoke(googleCredential: AuthCredential): Response<Boolean> {
        return authRepository.firebaseSignInWithGoogle(googleCredential)
    }
}