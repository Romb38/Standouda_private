package com.example.standouda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController





@Composable
fun SettingsListItem(item: Item,navController: NavController) {
    Button(
        onClick =
        {
            val errorMessage = "Message d'erreur"
        },//navController.navigate(item.name.lowercase()) },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = ButtonDefaults.buttonColors(Color.Black),
        shape = RectangleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            // Icone centrée à gauche
            Icon(
                painter = painterResource(id = R.drawable.icon_default),
                contentDescription = "Icon",
                modifier = Modifier.padding(16.dp)
            )

            // Titre à droite
            Text(
                modifier = Modifier.weight(1f),
                text = item.name,
                style = TextStyle(fontSize = 24.sp, color = Color.White),
            )

        }
    }
}

fun settingsGenerateList(): List<Item> {
    return List(Constants.SETTINGS_MENU.size) { index -> Item(Constants.SETTINGS_MENU[index]) }
}

@Composable
fun SettingsScreen(navController : NavController) {
    Column {
        val settingList by remember { mutableStateOf(settingsGenerateList()) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Afficher la TopBar
            Bar(name = stringResource(R.string.settings), toGo = "main",navController=navController)
            Box(
                modifier = Modifier.weight(1f)
            ) {
                // Afficher la liste d'items avec un espace en haut pour la TopBar
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(settingList.size) { index ->
                        SettingsListItem(item = settingList[index],navController)
                        HorizontalDivider(color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview(){
    val navControllerFactice = rememberNavController()

    SettingsScreen(navController = navControllerFactice)
}