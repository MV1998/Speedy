package com.example.speedy.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.speedy.database.LocalPairedDB
import com.example.speedy.navigation.ROUTE
import com.example.speedy.view_model.NoteViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUp(
    onBackToSignIn: () -> Unit,
    modifier: Modifier = Modifier, navController: NavController) {
    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }

    var eyeEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    var shouldShowProgress by rememberSaveable {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()

    val dataStore = LocalPairedDB

    if (shouldShowProgress) {
        val size =  48.dp

        Dialog(
            onDismissRequest = {}
        ) {
            Box(
                modifier = Modifier
                    .size(size)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }

    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(listOf(Color.Red, Color.Yellow))),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Sign Up", fontSize = 24.sp)
            Spacer(modifier = modifier.height(20.dp))
            Text("Create your account")
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

            Spacer(modifier = modifier.height(10.dp))

            TextField(value = confirmPassword,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    confirmPassword = it },
                placeholder = {
                    Text(text = "Confirm Password")
                }, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            Spacer(modifier = modifier.height(20.dp))

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                onClick = {
                    dataStore.saveData("email", email)
                    dataStore.saveData("password", password)
                    shouldShowProgress = true
                coroutineScope.launch {
                    delay(5000)
                    shouldShowProgress = false
                    navController.popBackStack()
                }
                }) {
                Text("Sign Up")
            }

            Text("Or", textAlign = TextAlign.Center, modifier = modifier.fillMaxWidth())

            Row {
                Text("Already have an account?", color = Color.White)
                Text("Sign In", color = Color.Blue, modifier = modifier.clickable {
                    onBackToSignIn()
                })
            }
        }
    }
}