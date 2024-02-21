package com.example.standouda

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

fun deleteFile(chemin: String): Boolean {
    val fichier = File(chemin)

    if (!fichier.exists()) {
        Log.e("fileManager","Le fichier n'existe pas.")
        return false
    }

    return try {
        fichier.delete()
    } catch (e: SecurityException) {
        Log.e("fileManager","Erreur de sécurité lors de la suppression du fichier : ${e.message}")
        false
    }
}


fun installApkFromFolder(context: Context, path: String) {
    val apkFile = File(path)
    Log.d("file", apkFile.toString())

    // Utilisez le FileProvider pour obtenir l'URI du fichier APK
    val apkUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", apkFile)
    Log.d("file", apkUri.toString())

    val installIntent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(apkUri, "application/vnd.android.package-archive")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    context.startActivity(installIntent)
}