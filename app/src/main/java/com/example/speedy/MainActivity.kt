package com.example.speedy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.speedy.data.InMemoryToDoRepositoryImpl
import com.example.speedy.data.PostifyRepositoryImpl
import com.example.speedy.database.LocalPairedDB
import com.example.speedy.database.room_post.PostDatabase
import com.example.speedy.navigation.Navigation
import com.example.speedy.retrofit.PostifyService
import com.example.speedy.ui.theme.SpeedyTheme
import com.example.speedy.view_model.NoteViewModel
import com.example.speedy.view_model.NoteViewModelFactory
import com.example.speedy.view_model.PostifyViewModel
import com.example.speedy.view_model.PostifyViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var postifyViewModel: PostifyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStore = LocalPairedDB
        val toDoRepository = InMemoryToDoRepositoryImpl(dataStore)
        val noteViewModelFactory = NoteViewModelFactory(toDoRepository)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory)[NoteViewModel::class.java]

        val postifyApi = PostifyService.getPostifyApiService()
        val postDatabase = PostDatabase.getPostDatabase(applicationContext)
        val postifyRepository = PostifyRepositoryImpl(applicationContext, postifyApi, postDatabase.postDao())
        val postifyViewModelFactory = PostifyViewModelFactory(postifyRepository)
        postifyViewModel = ViewModelProvider(this, postifyViewModelFactory)[PostifyViewModel::class.java]

        lifecycle.addObserver(noteViewModel)
        setContent{
            SpeedyTheme {
                Navigation(noteViewModel = noteViewModel, postifyViewModel = postifyViewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(noteViewModel)
    }
}
