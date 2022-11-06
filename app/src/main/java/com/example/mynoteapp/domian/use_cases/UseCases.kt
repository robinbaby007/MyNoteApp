package com.example.mynoteapp.domian.use_cases

data class UseCases(
    val oneTapSignIn: OneTapSignIn,
    val userAuthenticatedOrNot: UserAuthenticatedOrNot,
    val firebaseSignInWithGoogle: FirebaseSignInWithGoogle
)