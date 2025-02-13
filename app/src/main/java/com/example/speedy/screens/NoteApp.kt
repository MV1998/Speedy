package com.example.speedy.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.speedy.model.Note
import com.example.speedy.utilities.InternetConnectivity
import com.example.speedy.view_model.DrawerNavigationStates
import com.example.speedy.view_model.NoteViewModel
import com.example.speedy.view_model.PostifyViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NoteApp(
    navController: NavController, noteViewModel: NoteViewModel,
    postifyViewModel: PostifyViewModel
) {

    val number = remember {
        mutableIntStateOf(0)
    }

    val context = LocalContext.current

    val noteList = noteViewModel.list.observeAsState().value ?: mutableListOf<Note>()

    LaunchedEffect(key1 = true) {
        number.intValue += 1
        Log.d("TAG", "NoteApp: ${number.intValue}")
        noteViewModel.fetchData()
    }

    val drawerNavigationState = noteViewModel.drawerNavigationStates.collectAsState()

    val scope = rememberCoroutineScope()

    val drawerState =
        androidx.compose.material3.rememberDrawerState(
            initialValue = androidx.compose.material3.DrawerValue.Closed
        )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ListItem(icon = {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Speedy"
                    )
                }) {
                    Text("Speedy")
                }
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Home") },
                    selected = drawerNavigationState.value ==
                            DrawerNavigationStates.HOME,
                    onClick = {
                        scope.launch {
                            noteViewModel.updateDrawerNavigationState(DrawerNavigationStates.HOME)
                            drawerState.close()
                        }
                    })
                NavigationDrawerItem(
                    label = {
                        Text("Doggy Facts")
                    },
                    selected = drawerNavigationState.value ==
                            DrawerNavigationStates.DOGGIFY,
                    onClick = {
                        scope.launch {
                            noteViewModel.updateDrawerNavigationState(DrawerNavigationStates.DOGGIFY)
                            drawerState.close()
                        }
                    })
                NavigationDrawerItem(
                    label = {
                        Text("Anify Facts")
                    },
                    selected = drawerNavigationState.value ==
                            DrawerNavigationStates.ANIFY,
                    onClick = {
                        scope.launch {
                            noteViewModel.updateDrawerNavigationState(DrawerNavigationStates.ANIFY)
                            drawerState.close()
                        }
                    })
            }
        }, gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                NoteTopAppBar(title = "Note App", onClick = {
                    scope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            drawerState.open()
                        }
                    }
                }, modifier = Modifier)
            },
            floatingActionButton = {
                if (drawerNavigationState.value == DrawerNavigationStates.HOME) {
                    IconButton(
                        onClick = {
                            noteViewModel.selectedNote = null
                            navController.navigate("addUpdateNote/Create")
                        },
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .background(color = Color.Yellow),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "add",
                            tint = Color.Black
                        )
                    }
                }
            }
        ) { paddingValues ->
            when (drawerNavigationState.value) {
                DrawerNavigationStates.HOME -> {
                    if (noteList.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(paddingValues = paddingValues)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Top
                        ) {
                            items(noteList) {
                                androidx.compose.material3.ListItem(
                                    colors = ListItemDefaults.colors(
                                        containerColor = Color.DarkGray,
                                        headlineColor = Color.White,
                                        supportingColor = Color.White
                                    ),
                                    trailingContent = {
                                        IconButton(onClick = {
                                            noteViewModel.selectedNote = null
                                            noteViewModel.remove(it)
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "Delete",
                                                tint = Color.White
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onTap = { _ ->
                                                    noteViewModel.selectedNote = it
                                                    navController.navigate("addUpdateNote/Update")
                                                }
                                            )
                                        },
                                    headlineContent = {
                                        Text(it.title.trim(), fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold)
                                    }, supportingContent = {
                                        Text(it.description.trim())
                                    })
                                Divider(color = Color.White, thickness = 1.dp)
//                                Text(it.title, color = Color.White,
//                                    modifier = Modifier
//                                        .pointerInput(Unit) {
//                                            detectTapGestures(
//                                                onTap = { _ ->
//                                                    noteViewModel.selectedNote = it
//                                                    navController.navigate("addUpdateNote/Update")
//                                                },
//                                                onLongPress = { _ ->
//                                                    noteViewModel.selectedNote = null
//                                                    noteViewModel.remove(it)
//                                                }
//                                            )
//                                        }
//                                        .padding(2.dp)
//                                        .background(color = Color.Gray)
//                                        .fillMaxWidth()
//                                        .padding(20.dp),
//                                    fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text("No Notes")
                        }
                    }
                }

                DrawerNavigationStates.DOGGIFY -> doggify(
                    noteViewModel = noteViewModel,
                    paddingValues = paddingValues
                )

                DrawerNavigationStates.ANIFY -> postify(
                    postifyViewModel = postifyViewModel,
                    paddingValues = paddingValues
                )
            }
        }
    }
}


@Composable
fun ListItem() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopAppBar(title: String, onClick: () -> Unit, modifier: Modifier) {
    TopAppBar(
        title = {
            Text(text = title)
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Yellow,
            titleContentColor = Color.Black
        ),
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.Menu, contentDescription =
                    "Hamburder"
                )
            }
        }
    )
}

