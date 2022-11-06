package com.example.mynoteapp.domian.use_cases

import android.util.Log
import com.example.mynoteapp.domian.AuthRepository
import com.example.mynoteapp.utils.Response
import com.google.android.gms.auth.api.identity.BeginSignInResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAuthenticatedOrNot @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke(): Flow<Boolean> {
        return authRepository.isUserAuthenticatedInFirebase()
    }
}