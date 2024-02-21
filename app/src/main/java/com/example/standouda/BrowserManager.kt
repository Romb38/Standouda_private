package com.example.standouda

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaCodec.MetricsConstants.MIME_TYPE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.net.URL


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
        var result = ""

        result = wG.retrofitService.getInfos()


        return@runBlocking result
    } catch (e : Exception){
        Log.d("webError",e.toString())
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

@RequiresApi(Build.VERSION_CODES.O)
fun downloadFile(context: Context, url: String, fileName: String) {
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

    val id = downloadManager.enqueue(request)
}