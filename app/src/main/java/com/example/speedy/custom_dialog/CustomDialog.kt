package com.example.speedy.custom_dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CustomDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    onDismissButton: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
    }, confirmButton = {
            TextButton(onClick = {
                confirmButton()
            }) {
                Text(text = "Done")
            }
    },
        title = {
            Text("Title")
        }, text = {
            Text(text = "Sample Test here")
        }, dismissButton = {
            TextButton(onClick = {
                onDismissButton()
            }) {
                Text(text = "Dismiss")
            }
        })
}