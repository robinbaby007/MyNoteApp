package com.example.mynoteapp.di

import android.content.Context
import android.provider.Settings.Global.getString
import com.example.mynoteapp.AppApplication
import com.example.mynoteapp.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object Module {

    /*@Provides
    fun provideContext(application: AppApplication) = application.applicationContext*/

    @Provides
    fun provideContext(context: Context) = Identity.getSignInClient(context)

    @Provides
    fun provideSignInRequest(application: AppApplication) =
        BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(application.getString(R.string.web_api_key))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()


    @Provides
    fun provideSignUpRequest(application: AppApplication) =
        BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(application.getString(R.string.web_api_key))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

    @Provides
    fun provideGoogleSignInOptions(application: AppApplication) =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

    @Provides
    fun provideGoogleSignInClient(googleSignInOptions: GoogleSignInOptions,context: Context) = GoogleSignIn.getClient(context, googleSignInOptions)

}