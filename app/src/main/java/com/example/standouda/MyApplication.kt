package com.example.standouda

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

enum class ApplicationState {
    UNINSTALLED,
    IS_INSTALLING,
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
    var infoLink: String = "https://www.rainbowswingers.net/",
) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    @Ignore var state: ApplicationState = ApplicationState.UNINSTALLED

    @Composable
    fun AfficheAppIcon() {
        Image(
            painter = painterResource(id = R.drawable.default_app_icon),
            contentDescription = "Description de l'image",
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp)
        )
    }

    @Composable
    fun AffAppInteractButton() {
        if (this.state == ApplicationState.UPDATABLE){
            UpdateInteraction()
            return
        }

        if(this.state == ApplicationState.INSTALLED){
            IsInstalledInteraction()
            return
        }

        DownloadInteraction()
    }

    @Composable
    fun DownloadInteraction(){
        IconButton(
            onClick = { /*[TODO] Lancer le téléchargement de l'application et l'installation*/ },
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.download_24px),
                contentDescription = "Information",
                tint = Color.White
            )
        }
    }

    @Composable
    fun UpdateInteraction(){
        IconButton(
            onClick = { /*[TODO] Lancer la mise à jour de l'application*/ },
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.outline_update),
                contentDescription = "Information",
                tint = Color.White
            )
        }
    }

    @Composable
    fun IsInstalledInteraction(){
        val ctx = LocalContext.current
        IconButton(
            onClick = { toast(ctx,this.name +" is up to date") },
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
}