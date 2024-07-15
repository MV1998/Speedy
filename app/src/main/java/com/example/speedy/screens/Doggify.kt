package com.example.speedy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.speedy.utilities.InternetConnectivity
import com.example.speedy.view_model.BreedUiState
import com.example.speedy.view_model.NoteViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun doggify(modifier: Modifier = Modifier,
            paddingValues: PaddingValues,
            noteViewModel: NoteViewModel) {

    val breedState = noteViewModel.breeds.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        noteViewModel.fetchBreeds()
    }

    Surface(
        modifier = modifier.padding(paddingValues)
    ) {
        Column {
            Box(modifier = modifier
                .fillMaxHeight(.9f)
                .weight(.9f)
                .fillMaxWidth()) {
                when(breedState.value) {
                    is BreedUiState.Loading -> {
                        Surface(modifier = modifier.align(Alignment.Center)) {
                            CircularProgressIndicator()
                        }
                    }
                    is BreedUiState.Success -> {
                        val breedList = (breedState.value as BreedUiState.Success).data?.data ?: mutableListOf()
                        LazyColumn(modifier = modifier.pointerInput(Unit) {
                            detectTapGestures (

                            )
                        }) {
                            items(breedList) {
                                ListItem(secondaryText = {
                                    Text(text = it.attributes.description)
                                }, modifier = modifier
                                    .padding(3.dp)
                                    .background(color = Color.Cyan)
                                    .padding(4.dp)) {
                                    Text(text = it.attributes.name,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                    is BreedUiState.Error -> {
                        Text(text = "No Data Found!!!",
                            modifier = modifier.align(Alignment.Center))
                    }
                }
            }
            Box(modifier = modifier
                .fillMaxHeight(.1f)
                .weight(.1f)
                .fillMaxWidth()) {
                Row(modifier = modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    if (noteViewModel.page != 1) {
                        IconButton(onClick = { noteViewModel.previousPage() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription =
                            "left arrow")
                        }
                    }else {
                        Text(text = "No Previous Page")
                    }
                    Spacer(modifier = modifier.width(5.dp))
                    Text(text = noteViewModel.page.toString())
                    Spacer(modifier = modifier.width(5.dp))
                    if (noteViewModel.page != 29) {
                        IconButton(onClick = { noteViewModel.nextPage() }) {
                            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "right arrow")
                        }
                    }else {
                        Text(text = "No Next Page")
                    }
                }
            }
        }
    }
}