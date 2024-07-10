package com.example.speedy

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun Show(modifier: Modifier = Modifier) {

    var text by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    ViewUi(onClick = {
        text = it
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    })
}

@Composable
fun ViewUi(
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier) {

    var text = "Something"
    Column {
        Text(text = text)

        text.toCharArray().shuffle()
        Button(onClick = { onClick(text) }) {
            Text(text = "Click")
        }
    }
}