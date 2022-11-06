package com.example.mynoteapp.presentation.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mynoteapp.presentation.customViews.CustomLoginCard
import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mynoteapp.utils.Response
import com.example.mynoteapp.utils.Screens
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential


@Composable
fun Login(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {


    viewModel.launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credentials =
                        viewModel.signInClient.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = getCredential(googleIdToken, null)
                    viewModel.signInWithGoogle(googleCredentials)
                } catch (it: ApiException) {
                    print(it)
                }
            }
        }

    if (viewModel.isUserAuthenticated) {

        navController.apply {
            popBackStack()
            navigate(Screens.HomeScreen.route)
        }

    } else {

        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center)     {

            if (viewModel.isLoading)
                CircularProgressIndicator()
            else {
                CustomLoginCard(shape = MaterialTheme.shapes.large) {
                    viewModel.oneTap()
                }
            }
        }

    }


}


