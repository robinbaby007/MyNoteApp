package com.example.mynoteapp.utils

sealed class Response<out T> {
    object Loading : Response<Nothing>()
    data class Success<out T>(val data: T?) :  Response<T>()
    data class Failure<out T>(val e:String) :Response<T>()

}