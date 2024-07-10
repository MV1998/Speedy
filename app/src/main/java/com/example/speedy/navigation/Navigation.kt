package com.example.speedy.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.speedy.custom_dialog.CustomDialog
import com.example.speedy.screens.AddUpdateNote
import com.example.speedy.screens.NoteApp
import com.example.speedy.screens.SignIn
import com.example.speedy.screens.SignUp
import com.example.speedy.screens.Splash
import com.example.speedy.view_model.NoteViewModel
import com.example.speedy.view_model.PostifyViewModel


enum class ROUTE {
    SPLASH, SIGN_IN, SIGN_UP, NOTE_APP, EDIT, SHOW_DIALOG
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    noteViewModel: NoteViewModel,
    postifyViewModel: PostifyViewModel
) {
    NavHost(navController = navController, startDestination = "splash") {
        composable(route = ROUTE.SPLASH.name) {
            Splash(navController = navController)
        }

        composable(route = ROUTE.SIGN_IN.name) {
            SignIn(navController = navController)
        }

        composable(route = ROUTE.SIGN_UP.name) {
            SignUp({
                navController.popBackStack()
            },navController = navController)
        }

        composable(route = ROUTE.NOTE_APP.name) {
            NoteApp(navController = navController,
                noteViewModel, postifyViewModel = postifyViewModel)
        }

        composable(route = "addUpdateNote/{titleName}") {
            val titleName = it.arguments?.getString("titleName") ?: "No title"
            AddUpdateNote(navController = navController, titleName = titleName,
                noteViewModel = noteViewModel)
        }

        dialog(ROUTE.SHOW_DIALOG.name) {
            CustomDialog(onDismissRequest = {
                navController.popBackStack()
            }, confirmButton = {
                navController.popBackStack()
            }) {
                navController.popBackStack()
            }
        }
    }
}