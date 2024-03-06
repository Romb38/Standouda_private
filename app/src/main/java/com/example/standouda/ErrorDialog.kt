package com.example.standouda

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ErrorDialogState(
    var message: String
) {
    var shown by mutableStateOf(false)
        private set

    fun close() {
        shown = false
    }

}

@Composable
fun ErrorDialog(
    state: ErrorDialogState,
    onDismiss: () -> Unit = { state.close() }
) {
    if (!state.shown)
        return

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Erreur") },
        text = {
            Column {
                Text(
                    text = state.message,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onDismiss() },

            ) {
                Text("OK")
            }
        }
    )
}