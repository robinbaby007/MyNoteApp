package com.example.mynoteapp.domian.use_cases

import com.example.mynoteapp.domian.use_cases.home.ListNotes
import com.example.mynoteapp.domian.use_cases.login.FirebaseSignInWithGoogle
import com.example.mynoteapp.domian.use_cases.login.OneTapSignIn
import com.example.mynoteapp.domian.use_cases.login.UserAuthenticatedOrNot

data class UseCases(
    val oneTapSignIn: OneTapSignIn,
    val userAuthenticatedOrNot: UserAuthenticatedOrNot,
    val firebaseSignInWithGoogle: FirebaseSignInWithGoogle,
    val listNotes: ListNotes
)