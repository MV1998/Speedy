package com.example.speedy.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.speedy.utilities.InternetConnectivity
import com.example.speedy.view_model.PostUiState
import com.example.speedy.view_model.PostifyViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun postify(postifyViewModel: PostifyViewModel,
            paddingValues: PaddingValues,
            modifier: Modifier = Modifier) {

    val posts = postifyViewModel.posts.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        postifyViewModel.getPosts()
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var userId by remember {
        mutableStateOf("")
    }

    if (showDialog) {
        Dialog(onDismissRequest = {

        }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .background(color = Color.Blue)
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(10.dp)
            ) {
                Text(text = "Create Record", color = Color.White)
                Spacer(modifier = modifier.height(10.dp))
                TextField(value = title, onValueChange = {
                    title = it
                })
                Spacer(modifier = modifier.height(10.dp))
                TextField(value = description, onValueChange = {
                    description = it
                })
                Spacer(modifier = modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        showDialog = !showDialog
                    }) {
                        Text("Close")
                    }
                    Button(onClick = {
                        showDialog = !showDialog
                        postifyViewModel.addPost(title, description)
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }


    Scaffold(
        floatingActionButton = {
            IconButton(onClick = {
                showDialog = !showDialog
            }) {
                Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Thumb Up")
            }
        }
    ) {_ ->
        Surface(modifier = modifier.padding(paddingValues)) {
            when(val result = posts.value) {
                is PostUiState.Loading -> {
                    Box(modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is PostUiState.Error -> {
                    Row(modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.1f)
                        .background(color = Color.Red)
                        .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                        TextField(value = userId, onValueChange = {
                            userId = if (it.isNullOrEmpty()) "0" else it
                        }, modifier = modifier
                            .fillMaxWidth(.8f)
                            .weight(.8f),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                        IconButton(onClick = {
                            postifyViewModel.filterPosts(userId)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                }
                is PostUiState.Success -> {
                    Column(modifier = modifier
                        .fillMaxSize()) {
                        Row(modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.1f)
                            .weight(.1f)
                            .background(color = Color.Red)
                            .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            TextField(value = userId, onValueChange = {
                                userId = if (it.isNullOrEmpty()) "0" else it
                            }, modifier = modifier
                                .fillMaxWidth(.8f)
                                .weight(.8f),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                )
                            )
                            IconButton(onClick = {
                                postifyViewModel.filterPosts(userId)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                        }
                        LazyColumn(modifier = modifier
                            .fillMaxHeight(.9f)
                            .weight(.9f)
                            .fillMaxWidth()) {
                            items(result.post) {
                                ListItem(secondaryText = {
                                    Text(text = it.body, fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp)
                                }, modifier = modifier
                                    .clickable {
                                        postifyViewModel.deletePost(it.id)
                                    }
                                    .padding(5.dp)
                                    .background(color = Color.Cyan)
                                    .padding(5.dp)) {
                                    Text("${it.title} ${it.userId} ${it.id}", fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}