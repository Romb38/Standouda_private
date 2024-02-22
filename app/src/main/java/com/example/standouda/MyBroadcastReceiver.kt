package com.example.standouda

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyBroadcastReceiver(
    val snackbarHostState: SnackbarHostState,
    val context: Context
) : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("testIntent",intent.toString())
        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val errorCode = isCorrectlyDownload(context, intent)
            val path = getFilePath(context, intent).toString()
            Log.d("Download", path)
            if (errorCode == -1) {
                //[TODO] Tester
                Log.e("Download", "Échec du téléchargement, raison: $errorCode")
                Log.e("Download", path)
                CoroutineScope(Dispatchers.Main).launch {
                    snackbarHostState.showSnackbar("Download failed, error code : $errorCode")
                }
                deleteFile(path)
                return
            }

            CoroutineScope(Dispatchers.Main).launch {
                snackbarHostState.showSnackbar("Download successful")
            }

            Log.d("Download", "Téléchargement fini")
            installApkFromFolder(this.context, path)

        } else{
            // Téléchargement échoué
            Log.e("Download","Error - Download failed")
            CoroutineScope(Dispatchers.Main).launch {
                snackbarHostState.showSnackbar("Download failed")
            }
        }
    }

    fun isCorrectlyDownload(context: Context?, intent: Intent): Int{
        // Vérifie si le téléchargement s'est bien passé
        var output : Int = 0
        val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        val downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val pre_status = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            if (pre_status != -1){
                val status = cursor.getInt(pre_status)
                if (status == DownloadManager.STATUS_FAILED) {
                    // Téléchargement échoué, gérer l'erreur ici
                    val pre_reason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON)
                    if (pre_reason != -1){
                        val reason = cursor.getInt(pre_reason)
                        output = reason
                    }
                }
            } else {
                output = -1
            }
        }
        cursor.close()
        return output
    }

    @SuppressLint("Range")
    fun getFilePath(context: Context?, intent: Intent) : String?{
        val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        val downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor: Cursor = downloadManager.query(query)

        return if (cursor.moveToFirst()) {
            val status: Int = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            val localUri: String? = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))

            localUri?.substring(7) // Removing "file://
        } else {
            ""
        }
    }

}