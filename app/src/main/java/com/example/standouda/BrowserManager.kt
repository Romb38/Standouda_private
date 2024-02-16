package com.example.standouda

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.platform.UriHandler
import kotlinx.coroutines.runBlocking


fun isNetworkAvailable(ctx : Context) : Boolean{
    //Vérifie l'état de la connexion internet

    val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}


fun openURL(url : String, handler : UriHandler){
    handler.openUri(url)
}

fun getTxtFromURL(url: String) : String = runBlocking{
    val wG = WebGet(url)
    var result = ""

    result = wG.retrofitService.getInfos()


    return@runBlocking result
}

fun getAppInfos(url : String): String {
    //Récuperer les informations de l'application en ligne
    return getTxtFromURL(url)
}

fun getAppURLList():List<String>{
    val result = getTxtFromURL(Constants.GITHUB_APP_LINK)
    //Récupérer la liste des packqges disponibles en lignes
    return result.split("\n")
}