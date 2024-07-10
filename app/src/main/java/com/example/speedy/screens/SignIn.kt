package com.example.speedy.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.speedy.database.LocalPairedDB
import com.example.speedy.navigation.ROUTE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignIn(modifier: Modifier = Modifier,
           navController: NavController
) {

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var eyeEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(listOf(Color.Red, Color.Yellow))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign In", fontSize = 24.sp)
        Spacer(modifier = modifier.height(20.dp))
        TextField(value = email,
            trailingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "Person Image") },
            onValueChange = {
            email = it },
            placeholder = {
                Text(text = "Email")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        Spacer(modifier = modifier.height(10.dp))
        TextField(value = password,
            visualTransformation = if (eyeEnabled) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = {
                password = it },
            placeholder = {
                Text(text = "Password")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(onClick = {
                    eyeEnabled = !eyeEnabled
                }) {
                    Icon(imageVector = if (eyeEnabled) Icons.Filled.Check else Icons.Filled.Clear,
                        contentDescription = "Image")
                }
            }
        )
        Spacer(modifier = modifier.height(20.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            onClick = {
            if (isAuthenticated(email, password)) {
                navController.navigate(ROUTE.NOTE_APP.name) {
                    popUpTo(ROUTE.SIGN_IN.name) {
                        inclusive = true
                    }
                }
            }
        }) {
            Text("Sign In")
        }

        Spacer(modifier = modifier.height(20.dp))

        Row {
            Text("Don't have an account?", color = Color.White)
            Text("Sign Up", color = Color.Blue, modifier = modifier.clickable {
                navController.navigate(ROUTE.SIGN_UP.name) {
                    launchSingleTop = true
                }
            })
        }
    }
}

fun isAuthenticated(email : String, password: String) : Boolean {
    if (email.trim().isEmpty()) {
        Log.d("TAG", "email is empty: ")
        return false
    }

    if (password.trim().isEmpty()) {
        Log.d("TAG", "password is empty: ")
        return false
    }

    val dataStore = LocalPairedDB
    return !(dataStore.getData("email") != email || dataStore.getData("password") != password)
}