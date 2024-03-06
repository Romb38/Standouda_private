package com.example.standouda

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.launch

enum class ApplicationState {
    UNINSTALLED,
    UPDATABLE,
    INSTALLED
}

@Entity(tableName = "AppList")
class MyApplication(
    var name: String = "Untitled",
    var packageName : String = "com.example."+name,
    var author: String = "Unknown",
    var version: String = "",
    var icon: String = "", // Valeur par défaut pour l'icône
    var dlLink: String = "",
    var infoLink: String = "",
) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    @Ignore var state: ApplicationState = ApplicationState.UNINSTALLED


    @Composable
    fun AfficheAppIcon() {
        if(this.icon == "") {
            Image(
                painter = painterResource(id = R.drawable.default_app_icon),
                contentDescription = "Description de l'image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp)
            )
        } else {
            SubcomposeAsyncImage(
                model = this.icon,
                contentDescription = this.icon,
                loading = {
                          CircularProgressIndicator()
                },
                modifier = Modifier
                    .size(100.dp, 100.dp)
                    .padding(10.dp)
            )
        }
    }

    @Composable
    fun AffAppInteractButton(snackbarHostState : SnackbarHostState, state : Boolean) {
        if (this.state == ApplicationState.UPDATABLE){
            UpdateInteraction(snackbarHostState,state)
            return
        }

        if(this.state == ApplicationState.INSTALLED){
            IsInstalledInteraction(snackbarHostState)
            return
        }

        DownloadInteraction(snackbarHostState,state)
    }
    @Composable
    fun DownloadInteraction(snackbarHostState : SnackbarHostState, state : Boolean){
        val ctx = LocalContext.current
        val scope = rememberCoroutineScope()
        var isLoading by remember { mutableStateOf(state) }
        IconButton(
            onClick = {
                isLoading = true
                Log.d("appCheck",this.version)
                if (!isNetworkAvailable(ctx)){
                    scope.launch {
                        snackbarHostState.showSnackbar("Check internet connexion")
                    }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Downloading...")
                    }
                    downloadFile(ctx,"https://github.com/Romb38/StandoudApp/raw/main/test_apk.apk","test_apk.apk")

                    Constants.APP_INSTALLED = this
                }
                      },
        ) {
            if (isLoading){
                CircularProgressIndicator()
            } else {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.download_24px),
                    contentDescription = "Information",
                    tint = Color.White
                )
            }
        }
    }

    @Composable
    fun UpdateInteraction(snackbarHostState : SnackbarHostState, state : Boolean){
        val ctx = LocalContext.current
        val scope = rememberCoroutineScope()
        var isLoading by remember { mutableStateOf(state) }
        IconButton(
            onClick = {
                Log.d("appCheck",this.version)
                if (!isNetworkAvailable(ctx)){
                    scope.launch {
                        snackbarHostState.showSnackbar("Check internet connexion")
                    }
                } else {
                    isLoading = true
                    scope.launch {
                        snackbarHostState.showSnackbar("Downloading...")
                    }
                    downloadFile(ctx,"https://github.com/Romb38/StandoudApp/raw/main/test_apk.apk","test_apk.apk")

                    //[TODO] Bug de rechargement de la page lors de la mise à jour
                    Constants.APP_INSTALLED = this
                    AppDataBase.getDatabase(ctx).AppDAO().removeAppByPackageName(this.packageName)
                }


                      },
        ) {

            if (isLoading){
                CircularProgressIndicator()
            } else {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.outline_update),
                    contentDescription = "Information",
                    tint = Color.White
                )
            }

        }
    }

    @Composable
    fun IsInstalledInteraction(snackbarHostState : SnackbarHostState){

        val scope = rememberCoroutineScope()
        val message = this.name + " is up to date"

        IconButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            },
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.done_24px),
                contentDescription = "Information",
                tint = Color.White
            )
        }
    }

    fun isInstalled(context: Context) : Boolean {
        //On vérifie si une application est installée ou non
        return try {
            val packageManager = context.packageManager
            packageManager.getPackageInfo(this.packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    override fun toString(): String {
        return this.name
    }


    fun isEmpty() : Boolean{
        return this.packageName == ""
    }
}