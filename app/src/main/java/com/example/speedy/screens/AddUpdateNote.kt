package com.example.speedy.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.speedy.model.Note
import com.example.speedy.view_model.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUpdateNote(navController: NavController, titleName: String,
                  noteViewModel: NoteViewModel) {

    val note = remember {
        mutableStateOf(noteViewModel.selectedNote ?: Note(title = "", description = ""))
    }

    var title by remember {
        mutableStateOf(note.value.title)
    }

    var description by remember {
        mutableStateOf(note.value.description)
    }

    val titleInteraction = remember {
        MutableInteractionSource()
    }

    val titleFocusedState = titleInteraction.collectIsFocusedAsState()

    val descriptionInteraction = remember {
        MutableInteractionSource()
    }

    val descriptionFocusedState = descriptionInteraction.collectIsFocusedAsState()


    BackHandler {
        Log.d("AddUpdateNote", "AddUpdateNote: ")
        if (titleName != "Create") {
            noteViewModel.update(note.value)
        }else {
            noteViewModel.add(note.value)
        }
        navController.popBackStack()
    }

    Scaffold(
        contentColor = Color.White,
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                Text(text = titleName)
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }, actions = {
                    if (titleFocusedState.value) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Check")
                        }
                    }else if (descriptionFocusedState.value) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "KeyboardArrowLeft")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "KeyboardArrowRight")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Check")
                        }
                    }else {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "ThumbUp")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "MoreVert")
                        }
                    }
                })
        },
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(5.dp),
            verticalArrangement = Arrangement.Top) {
            TextField(
                interactionSource = titleInteraction,
                textStyle = TextStyle(
                    fontSize = 24.sp
                ),
                placeholder = {
                    Text(text = "Title")
                },
                colors = TextFieldDefaults.colors(
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                maxLines = 3,
                value = title,
                onValueChange =  {
                title = it
                note.value.title = title
            })
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start) {
                Text(text = "17 July 12:57 PM",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 15.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("0 Characters",
                    color = Color.Gray,
                    fontSize = 12.sp,
                )
            }
            TextField(
                interactionSource = descriptionInteraction,
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                placeholder = {
                    Text(text = "Start typing")
                },
                colors = TextFieldDefaults.colors(
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxSize(),
                value = description, onValueChange =  {
                    description = it
                    note.value.description = description
                })
        }
    }
}