package com.example.speedy.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedy.data.PostifyRepository
import com.example.speedy.model.postify.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random


sealed class PostUiState {
    object Loading: PostUiState()
    data class Success(val post : List<Post>) : PostUiState()
    data class Error(val error : String) : PostUiState()
}

sealed class AddPostUiState {
    object Loading: AddPostUiState()
    data class Success(val post : Post) : AddPostUiState()
    data class Error(val error : String) : AddPostUiState()
}

class PostifyViewModel(private val postifyRepository: PostifyRepository) : ViewModel() {

    private val _posts = MutableStateFlow<PostUiState>(PostUiState.Loading)
    val posts : StateFlow<PostUiState> get() = _posts.asStateFlow()


   fun getPosts() {
        viewModelScope.launch {
            try {
                _posts.emit(PostUiState.Loading)
                postifyRepository.getOperation()?.let {
                    _posts.emit(PostUiState.Success(it))
                }
            }catch (e : Exception) {
                _posts.emit(PostUiState.Error("Exception Found!!!"))
            }
        }
    }

    fun addPost(title : String, description : String) {
        viewModelScope.launch {
            postifyRepository.postOperation(Post(
                title = title,
                body = description,
                userId = Random.nextInt(1000),
                id = Random.nextInt(1000)
            ))?.let {
                postifyRepository.getOperation()?.let {
                    _posts.emit(PostUiState.Success(it))
                }
            }
        }
    }

    fun filterPosts(userId : String) {
        viewModelScope.launch {
            try {
                _posts.emit(PostUiState.Loading)
                if (userId != "0") {
                    postifyRepository.filterOperation(userId = userId.toInt())?.let {
                        Log.d("TAG", "filterPosts: ${it.size}")
                        _posts.emit(PostUiState.Success(it))
                    }
                }else {
                    postifyRepository.getOperation()?.let {
                        _posts.emit(PostUiState.Success(it))
                    }
                }
            }catch (e : Exception) {
                _posts.emit(PostUiState.Error("Exception Found!!!"))
            }
        }
    }

    fun deletePost(id : Int) {
        viewModelScope.launch {
            try{
                postifyRepository.deleteOperation(id).let {
                    Log.d("TAG", "deletePost: ${it}")
                }
            }catch (e : Exception) {
                Log.d("TAG", "deletePost: Exception Found!!!")
            }
        }
    }
}