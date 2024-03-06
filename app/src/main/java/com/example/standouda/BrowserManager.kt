package com.example.standouda

import android.app.DownloadManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Environment
import android.util.Log
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
    try {
        val wG = WebGet(url)
        return@runBlocking wG.retrofitService.getInfos()
    } catch (e: Exception) {
        Log.d("webError", e.toString())
        return@runBlocking ""
    }
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

fun downloadFile(context: Context, url: String, fileName: String) {
    //[TODO] Assurer que l'URL est correcte

    // Télécharge un fichier en ligne et le stocke dans le dossier de stockage interne de l'application

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val uri = Uri.parse(url)
    Log.d("Download", "URL: $url")
    Log.d("Download", "File Name: $fileName")
    val request = DownloadManager.Request(uri)
        .setTitle(fileName)
        .setDescription("Downloading...")
        .setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileName)
        .setMimeType("application/vnd.android.package-archive")

    downloadManager.enqueue(request)
}