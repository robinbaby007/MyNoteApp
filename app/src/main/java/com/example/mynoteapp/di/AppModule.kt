package com.example.mynoteapp.di

import android.app.Application
import android.content.Context
import com.example.mynoteapp.R
import com.example.mynoteapp.data.AuthRepositoryImpl
import com.example.mynoteapp.data.HomeRepositoryImpl
import com.example.mynoteapp.domian.AuthRepository
import com.example.mynoteapp.domian.HomeRepository
import com.example.mynoteapp.domian.use_cases.login.FirebaseSignInWithGoogle
import com.example.mynoteapp.domian.use_cases.login.OneTapSignIn
import com.example.mynoteapp.domian.use_cases.UseCases
import com.example.mynoteapp.domian.use_cases.home.ListNotes
import com.example.mynoteapp.domian.use_cases.login.UserAuthenticatedOrNot
import com.example.mynoteapp.utils.Constants.SIGN_IN_REQUEST
import com.example.mynoteapp.utils.Constants.SIGN_UP_REQUEST
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseFireStore() = Firebase.firestore


    @Provides
    @Singleton
    fun provideOneTapClient(
        context: Context
    ) = Identity.getSignInClient(context)

    @Named(SIGN_IN_REQUEST)
    @Provides
    @Singleton
    fun provideSignInRequest(application: Application) =
        BeginSignInRequest.builder().setPasswordRequestOptions(
            BeginSignInRequest.PasswordRequestOptions.builder().setSupported(true).build()
        ).setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(application.getString(R.string.web_api_key))
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(true).build()
        ).build()


    @Provides
    @Singleton
    @Named(SIGN_UP_REQUEST)
    fun provideSignUpRequest(application: Application) =
        BeginSignInRequest.builder().setPasswordRequestOptions(
            BeginSignInRequest.PasswordRequestOptions.builder().setSupported(true).build()
        ).setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(application.getString(R.string.web_api_key))
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false).build()
        ).build()

    @Provides
    @Singleton
    fun provideGoogleSignInOptions(application: Application) =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id)).requestEmail()
            .build()

    @Provides
    @Singleton
    fun provideGoogleSignInClient(googleSignInOptions: GoogleSignInOptions, context: Context) =
        GoogleSignIn.getClient(context, googleSignInOptions)

    @Provides
    @Singleton
    fun provideAuthRepository(
        @Named(SIGN_IN_REQUEST)
        signInRequest: BeginSignInRequest,
        @Named(SIGN_UP_REQUEST)
        signUpRequest: BeginSignInRequest,
        oneTapClient: SignInClient,
        fbAuth: FirebaseAuth,
        db: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(signInRequest, signUpRequest, oneTapClient, fbAuth, db)


    @Provides
    @Singleton
    fun provideUseCase(authRepository: AuthRepository,homeRepository: HomeRepository): UseCases =
        UseCases(
            OneTapSignIn(authRepository),
            UserAuthenticatedOrNot(authRepository),
            FirebaseSignInWithGoogle(authRepository),
            ListNotes(homeRepository)
        )


    @Provides
    @Singleton
    fun provideHomeRepository(fireStore: FirebaseFirestore, firebaseAuth: FirebaseAuth): HomeRepository =
        HomeRepositoryImpl(fireStore,firebaseAuth)

}