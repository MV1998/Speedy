package com.example.speedy.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.speedy.database.LocalPairedDB
import com.example.speedy.navigation.ROUTE
import com.example.speedy.view_model.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUpdateNote(navController: NavController, titleName: String,
                  noteViewModel: NoteViewModel) {
    val description = rememberSaveable {
        mutableStateOf(noteViewModel.selectedNote ?: "")
    }
    val dataStore = LocalPairedDB

    BackHandler() {
        Log.d("AddUpdateNote", "AddUpdateNote: ")
//        if (navController.currentDestination?.route != ROUTE.NOTE_APP.name) {
//
//        }
        if (titleName != "Create") {
            noteViewModel.update(noteViewModel.selectedNote!!, description.value)
        }else {
            noteViewModel.add(description.value)
        }
        navController.popBackStack()
    }

    Scaffold(
        contentColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                Text(text = titleName)
            })
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            TextField(value = description.value, onValueChange =  {
                description.value = it
            }, modifier = Modifier
                .fillMaxSize(),
                shape = TextFieldDefaults.shape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Gray,
                    cursorColor = Color.Black
                ))
        }
    }
}