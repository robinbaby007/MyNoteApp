package com.example.mynoteapp.presentation.login

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynoteapp.domian.AuthRepository
import com.example.mynoteapp.utils.Response
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository, val signInClient: SignInClient
) : ViewModel() {

    val isUserAuthenticated = authRepository.isUserAuthenticatedInFirebase

    private var oneTapSignInResponse by mutableStateOf<Response<BeginSignInResult>>(Response.Loading)

    var signInWithGoogleResponse by mutableStateOf<Response<Boolean>>(Response.Success(false))

    private var isLoading by mutableStateOf(true)

    var isError by mutableStateOf("")

    lateinit var launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>


    fun oneTap() {
        viewModelScope.launch {

            when (val oneTap = authRepository.oneTapSignInWithGoogle()) {

                is Response.Loading -> {
                    isLoading = true
                }

                is Response.Success -> {
                    isLoading = false
                    oneTapSignInResponse = oneTap
                    launch(oneTap.data!!)
                }

                is Response.Failure -> {
                    isLoading = false
                    isError = oneTap.e.toString()
                    Log.e("LogMeDude", isError)
                }

            }
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) {
        viewModelScope.launch {
            signInWithGoogleResponse = authRepository.firebaseSignInWithGoogle(googleCredential)
        }
    }


    private fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
        Log.e("LogMeDude", "launched")
    }

}