package com.hfad.weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp

@Composable
fun DialogSearch(
    error: Boolean,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val dialogText = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(dialogText.value) }) { Text(text = "OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(text = "Cancel") }
        },
        title = { Text(text = "Введите название города:") },
        text = {
            Column {
                TextField(
                    value = dialogText.value,
                    onValueChange = { dialogText.value = it },
                    placeholder = { Text("Например: Moscow") },
                    isError = error,
                    supportingText = { if (error) Text("Город не найден") }
                )
            }
        }
    )
}