package com.example.standouda

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

enum class ApplicationState {
    UNINSTALLED,
    IS_INSTALLING,
    UPDATABLE,
    INSTALLED
}
class MyApplication(
    val name: String = "Untitled",
    val author: String = "Unknown",
    val icon: String = "", // Valeur par défaut pour l'icône
    val dlLink: String = "",
    val infoLink: String = "https://www.rainbowswingers.net/",
    private val state: ApplicationState = ApplicationState.UNINSTALLED
) {
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
            onClick = { /*TODO*/ },
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
            onClick = { /*TODO*/ },
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
}