package com.example.speedy.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.speedy.data.PostifyRepository

class PostifyViewModelFactory(val repository: PostifyRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostifyViewModel(repository) as T
    }
}