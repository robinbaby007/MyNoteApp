package com.example.mynoteapp.utils

sealed class Screens(val route:String) {


    object LoginScreen : Screens("LoginScreen")
    object HomeScreen : Screens("HomeScreen")


}