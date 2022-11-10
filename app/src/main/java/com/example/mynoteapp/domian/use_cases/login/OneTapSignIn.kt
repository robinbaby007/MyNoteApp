package com.example.mynoteapp.domian.use_cases.login

import com.example.mynoteapp.domian.AuthRepository
import com.example.mynoteapp.utils.Response
import com.google.android.gms.auth.api.identity.BeginSignInResult
import javax.inject.Inject

class OneTapSignIn @Inject constructor(private val authRepository: AuthRepository)  {

 suspend operator fun invoke(): Response<BeginSignInResult> {
  return authRepository.oneTapSignInWithGoogle()
 }

}