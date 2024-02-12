package com.example.standouda

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun openUrlCallback(callback: (String) -> Unit): (String) -> Unit {
    val launcher = rememberLauncher()
    return { url ->
        if (launcher != null) {
            launcher.launch(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } else {
        }
    }
}

@Composable
private fun rememberLauncher(): ActivityResultLauncher<Intent>? {
    val activity = LocalContext.current as? ComponentActivity
    return remember(activity) {
        activity?.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    }
}