package com.example.mynoteapp.presentation.home

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
 import com.example.mynoteapp.domian.use_cases.UseCases
import com.example.mynoteapp.utils.Constants
import com.example.mynoteapp.utils.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
 import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCases: UseCases,private val firebaseAuth: FirebaseAuth)  : ViewModel() {

    private val _noteList = mutableStateListOf<String>()
    val noteList : List<String> =_noteList
    var isLoading by mutableStateOf(true)
    var userImage by mutableStateOf(Uri.EMPTY)


   init {

       viewModelScope.launch {
           useCases.listNotes.invoke().collect{

               when(it){

                   is Response.Loading -> isLoading = true
                   is Response.Success ->{
                       _noteList.clear()
                       it.data?.forEach{snapShot->
                           snapShot.data?.values?.forEach {note->
                               _noteList.add(note.toString())
                           }
                       }
                       isLoading = false
                   }
                   is Response.Failure ->{
                       isLoading = false
                   }
               }
           }
       }
       userImage=firebaseAuth.currentUser?.photoUrl
   }


}