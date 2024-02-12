package com.example.standouda

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun AboutView(navController: NavController) {
    var showDialog by remember { mutableStateOf(true) }
    Column() {
        val appName = R.string.app_name

        Bar(name = Constants.SETTINGS_MENU[0], toGo = "settings", navController = navController)

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Afficher l'icône de l'application
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Icon de l'application",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )
            }
            HorizontalDivider(color = Color.Gray)

            Spacer(modifier = Modifier.height(50.dp))

            AppInformation()

            Row() {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.github_mark_white),
                            contentDescription = "Icon",
                            modifier = Modifier.size(50.dp),
                            tint = Color.White
                        )
                    }
                }
            }

            // Ajouter d'autres informations si nécessaire
        }

    }
}

@Composable
fun AppInformation() {
    val appName = stringResource(id = R.string.app_name)
    val pseudo = stringResource(id = R.string.pseudo)
    val version = stringResource(id = R.string.version)

    // Style de texte pour les titres
    val titleTextStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Normal)

    // Style de texte pour les contenus
    val contentTextStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Normal)
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier) {
            // Version de l'application
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "$version",
                style = contentTextStyle.copy(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Pseudo
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "$appName by $pseudo",
                style = contentTextStyle.copy(fontSize = 16.sp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewAboutView() {
    val navControllerFactice = rememberNavController()
    AboutView(navControllerFactice)
}
