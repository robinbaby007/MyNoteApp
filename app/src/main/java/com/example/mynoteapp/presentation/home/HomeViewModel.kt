package com.example.mynoteapp.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
 import com.example.mynoteapp.domian.use_cases.UseCases
import com.example.mynoteapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
 import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCases: UseCases)  : ViewModel() {

    private val _noteList = mutableStateListOf<String>()
    val noteList : List<String> =_noteList


   init {

       viewModelScope.launch {
           useCases.listNotes.invoke().collect{
               _noteList.clear()
               it.forEach{snapShot->
                   snapShot.data?.values?.forEach {note->
                        _noteList.add(note.toString())
                   }
               }
           }
       }


   }


}