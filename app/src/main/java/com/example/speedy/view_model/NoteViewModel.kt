package com.example.speedy.view_model

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedy.data.ToDoRepository
import com.example.speedy.model.Data
import com.example.speedy.model.breed.Breeds
import com.example.speedy.retrofit.FactsService
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//livedata, flow, stateflow, shared flow, room db, mvvm, coroutine, thread,
// looper, message queue

// dele

sealed class FactUiState {
    data object Loading : FactUiState()
    data class Success(val data : Data?) : FactUiState()
    data class Error(val error : String) : FactUiState()
}


sealed class BreedUiState {
    data object Loading : BreedUiState()
    data class Success(val data : Breeds?) : BreedUiState()
    data class Error(val error : String) : BreedUiState()
}


enum class DrawerNavigationStates {
    HOME, DOGGIFY, ANIFY
}


class NoteViewModel(val toDoRepository: ToDoRepository): ViewModel(), LifecycleEventObserver {

    private var _list = MutableLiveData<MutableList<String>>()
    val list : LiveData<MutableList<String>> get() = _list

    var selectedNote : String? = null

    private val _fact = MutableStateFlow<FactUiState>(FactUiState.Loading)
    val fact : StateFlow<FactUiState> get() = _fact.asStateFlow()


    private val _breeds = MutableStateFlow<BreedUiState>(BreedUiState.Loading)
    val breeds : StateFlow<BreedUiState> get() = _breeds.asStateFlow()

    val factsApis by lazy {
        FactsService.getFactApi()
    }

    private val _drawerNavigationState = MutableStateFlow<DrawerNavigationStates>(DrawerNavigationStates.HOME)
    val drawerNavigationStates : StateFlow<DrawerNavigationStates> get() = _drawerNavigationState.asStateFlow()


    var page = 1

    fun add(item: String) {
        toDoRepository.addToDo(item)
       // _list.value =  toDoRepository.getAllToDo().value
    }

    fun update(oldItem : String, newItem : String) {
        toDoRepository.updateToDo(oldItem.trim(), newItem.trim())
    }

    fun remove(item : String) {
        toDoRepository.removeToDo(item)
        _list.postValue(toDoRepository.getAllToDo())
        Log.d("TAG", "remove: $item")
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("TAG", "onStateChanged: ${event.name}")
        if (event == Lifecycle.Event.ON_RESUME) {
            _list.postValue(toDoRepository.getAllToDo())
        }
    }

    init {
       fetchData()
    }

    fun updateDrawerNavigationState(state : DrawerNavigationStates) {
        viewModelScope.launch {
            _drawerNavigationState.emit(state)
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                _fact.emit(FactUiState.Loading)
                delay(3000)
                val data = async {
                    factsApis.getFacts()
                }
                val result = data.await().body()
                _fact.emit(FactUiState.Success(result).also {
                    Log.d("TAG", "fetchData: ${it.data?.data?.get(0)?.attributes?.body ?: "No Data"}")
                })
            }catch (e : Exception) {
                _fact.emit(FactUiState.Error("Exception Found!!!"))
            }
        }
    }

    fun previousPage() {
        if (page != 1) {
            page -= 1
            fetchBreeds()
        }
    }

    fun nextPage() {
        if (page != 29) {
            page += 1
            fetchBreeds()
        }
    }

    fun fetchBreeds() {
        viewModelScope.launch {
            try {
                _breeds.emit(BreedUiState.Loading)
                delay(1000)
                val data = async {
                    factsApis.breeds(page)
                }
                val result = data.await().body()
                _breeds.emit(BreedUiState.Success(result))
            }catch (e : Exception) {
                _breeds.emit(BreedUiState.Error("Exception Found!!"))
            }
        }
    }

//    fun fetchData() {
//        viewModelScope.launch {
//            while (true) {
//                try {
//                    _fact.emit(FactUiState.Loading)
//                    delay(3000)
//                    val data = async {
//                        factsApis.getFacts()
//                    }
//                    val result = data.await().body()
//                    _fact.emit(FactUiState.Success(result).also {
//                        Log.d("TAG", "fetchData: ${it.data?.data?.get(0)?.attributes?.body ?: "No Data"}")
//                        })
//                        delay(10000)
//                }catch (e : Exception) {
//                    _fact.emit(FactUiState.Error("Exception Found!!!"))
//                }
//            }
//        }
//    }

}