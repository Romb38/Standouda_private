package com.example.standouda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.standouda.ui.theme.StandoudaTheme
import com.example.standouda.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StandoudaTheme {
                navigation()
            }
        }
    }
}

// Définition de votre modèle Item
data class Item(val name: String)

@Composable
fun Standoudapp(navController: NavController){

    // État de la liste des items

    var itemList by remember { mutableStateOf(generateItemList(20)) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Afficher la TopBar
        TopBar(navController = navController)
        Box(
            modifier = Modifier.weight(1f)
        ) {
            // Afficher la liste d'items avec un espace en haut pour la TopBar
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(itemList.size) { index ->
                    ListItem(item = itemList[index])
                }
            }
            // Bouton de rafraîchissement en bas à droite
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                RefreshButton()
            }
        }


    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier,navController: NavController){
// Afficher le rectangle violet en haut de l'écran avec du texte à l'intérieur
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = colorResource(id = R.color.purple_200)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.app_name),
                style = TextStyle(fontSize = 24.sp, color = Color.White),
            )
            MoreOptionButton(navController = navController)
        }
    }
}


@Composable
fun RefreshButton(modifier: Modifier = Modifier){
    Button(
        modifier = Modifier
            .padding(40.dp)
            .size(80.dp),
        shape = CircleShape,
        onClick = {/*TODO*/ },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.outline_update),
            contentDescription = "Settings Icon",
            modifier = Modifier.size(140.dp)
        )
    }

}


@Composable
fun MoreOptionButton(modifier: Modifier = Modifier,navController: NavController){
    IconButton(
        modifier = Modifier.padding(16.dp),
        onClick = {navController.navigate("settings")}
    )
    {
        Icon(
            modifier = Modifier.size(60.dp),
            painter = painterResource(id = R.drawable.settings_24px), // Assurez-vous de mettre l'icône appropriée
            contentDescription = stringResource(R.string.more_options),
            tint = Color.White
        )
    }
}

// Générateur de liste d'items pour la démonstration
fun generateItemList(nb : Int): List<Item> {
    return List(nb) { index -> Item("Item_${index + 1}") }
}

@Composable
fun ListItem(item: Item) {
    Box(
        modifier = Modifier
            .background(Color.Black)
    ){
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = item.name,
            style = TextStyle(fontSize = 24.sp, color = Color.White)
        )
    }
    HorizontalDivider(color = Color.Gray)
}



@Preview
@Composable
fun navigation() {
    val navController = rememberNavController()

    // Utilisez NavHost pour gérer la navigation
    NavHost(navController = navController, startDestination = "main") {
        // Écran principal
        composable("main") {
            Standoudapp(navController)
        }
        // Écran des paramètres
        composable("settings") {
            SettingsScreen(navController=navController)
        }
        //About section
        composable("about"){
            AboutView(navController = navController)
        }
    }
}