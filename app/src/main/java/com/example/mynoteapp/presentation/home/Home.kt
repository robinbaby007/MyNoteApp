package com.example.mynoteapp.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mynoteapp.presentation.login.LoginViewModel
import com.example.mynoteapp.utils.Constants
import kotlin.random.Random


@Composable
fun Home(navController: NavController,  viewModel: HomeViewModel = hiltViewModel() ) {

     Log.e(Constants.APP_TAG, "noteSizeHome- ${viewModel.noteList.size}" )

    LazyColumn {

        items(
            items = viewModel.noteList,
            key ={
                it.toString()
            }
        ){ note->
            Log.e(Constants.APP_TAG, "noteItem- $note" )

            Text(text =note, color = Color.Black, fontSize = 25.sp)

        }

    }



}