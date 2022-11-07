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
import com.example.mynoteapp.domian.use_cases.UseCases
import com.example.mynoteapp.utils.Constants
import com.example.mynoteapp.utils.Response
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: UseCases,
    val signInClient: SignInClient,
    db: FirebaseFirestore,
    firebaseAuth: FirebaseAuth

) : ViewModel() {

    var isUserAuthenticated by mutableStateOf(false)

    private var oneTapSignInResponse by mutableStateOf<Response<BeginSignInResult>>(Response.Loading)

    var isOneTapSignInSuccess by mutableStateOf(false)

    var isLoading by mutableStateOf(false)

    private var isError by mutableStateOf("")

    lateinit var launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>

    init {

        viewModelScope.launch {

            useCases.userAuthenticatedOrNot.invoke().collect {
                isUserAuthenticated = it


            }

        }
    }

    fun oneTap() {
        viewModelScope.launch {

            when (val oneTap = useCases.oneTapSignIn.invoke()) {

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
                }

            }


        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) {
        viewModelScope.launch {


            when (val signInWithGoogleResponseTemp =
                useCases.firebaseSignInWithGoogle(googleCredential)) {

                is Response.Loading -> isLoading = true
                is Response.Success -> {
                    isLoading = false
                    isOneTapSignInSuccess = signInWithGoogleResponseTemp.data ?: false


                }
                is Response.Failure -> {
                    isLoading = false
                    isError = signInWithGoogleResponseTemp.e.toString()
                }

            }

            /* useCases.userAuthenticatedOrNot.invoke().collect {
                 isUserAuthenticated = it
                 Log.e("invokedd","$it" )

             }*/

        }


    }


    private fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

}