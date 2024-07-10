package com.example.speedy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.speedy.database.LocalPairedDB
import com.example.speedy.navigation.ROUTE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Splash(navController: NavController) {

    val dataStore = LocalPairedDB
    dataStore.initialize(LocalContext.current.applicationContext)
    val splashTimeOut = remember {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            if (dataStore.getData("email").isNullOrEmpty()) {
                navController.navigate(ROUTE.SIGN_IN.name) {
                    popUpTo(ROUTE.SPLASH.name) {
                        inclusive = true
                    }
                }
            }else {
                navController.navigate(ROUTE.NOTE_APP.name) {
                    popUpTo(ROUTE.SPLASH.name) {
                        inclusive = true
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            splashTimeOut.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize().
        background(brush = Brush.linearGradient(listOf(Color.Red, Color.Yellow))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Splash Screen")
    }
}