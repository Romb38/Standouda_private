package com.example.standouda

import androidx.compose.ui.platform.UriHandler

fun openURL(url : String, handler : UriHandler){
    handler.openUri(url)
}