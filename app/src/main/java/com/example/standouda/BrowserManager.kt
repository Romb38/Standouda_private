package com.example.standouda

import androidx.compose.ui.platform.UriHandler

fun openURL(url : String, handler : UriHandler){
    handler.openUri(url)
}

fun getAppInfos(url : String): String {
    //[TODO] Récuperer les informations de l'application en ligne

    return "1: Standouda\n" +
            "2: com.example.standouda\n"+
            "3: Romb38\n" +
            "4: 1.1.0\n" +
            "5: \n" +
            "6: https://github.com/Romb38/Standouda_private"
}

fun getAppURLList():List<String>{
    //[TODO] Récupérer la liste des packqges disponibles en lignes
    return listOf("1","2","3","4")
}