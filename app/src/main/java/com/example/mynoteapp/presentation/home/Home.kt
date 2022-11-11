package com.example.mynoteapp.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.mynoteapp.R
import com.example.mynoteapp.presentation.customViews.StaggeredVerticalGrid
import com.example.mynoteapp.presentation.login.LoginViewModel
import com.example.mynoteapp.utils.Constants
import com.example.mynoteapp.utils.Response
import javax.inject.Inject
import kotlin.random.Random


@Composable
fun Home(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    Log.e(Constants.APP_TAG, "noteSizeHome- ${viewModel.noteList.size}")
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else {

            Column(Modifier.fillMaxSize()) {
                SearchBar(Modifier,viewModel)
                NoteList(viewModel)
            }

        }

    }
}

@Composable
fun SearchBar(modifier: Modifier,viewModel: HomeViewModel) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(10.dp), shape = RoundedCornerShape(15.dp)
    ) {

        Row(modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 10.dp),horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_vertical_bars),
                contentDescription = "bars"
            )
            Text(text = LocalContext.current.getString(R.string.search_your_notes), style = MaterialTheme.typography.bodyLarge)
            AsyncImage(
                model = viewModel.userImage,
                contentDescription = null,
                modifier.clip(CircleShape).size(35.dp),
            )
        }

    }

}

@Composable
fun NoteList(viewModel: HomeViewModel){
    LazyColumn(Modifier.fillMaxSize()) {
        item {}
        item {

            StaggeredVerticalGrid(
                maxColumnWidth = 200.dp, modifier = Modifier.padding(4.dp)
            ) {

                viewModel.noteList.forEach { note ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(.5.dp, Color.Gray)

                    ) {

                        Log.e(Constants.APP_TAG, "noteItem- $note")
                        Text(
                            text = note,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(7.dp)
                        )

                    }
                }

            }
        }

    }
}
