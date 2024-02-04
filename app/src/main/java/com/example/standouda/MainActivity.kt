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
import com.example.standouda.ui.theme.StandoudaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StandoudaTheme {
                DiceRollerApp()
            }
        }
    }
}

// Définition de votre modèle Item
data class Item(val name: String)

@Preview
@Composable
fun DiceRollerApp(){

    // État de la liste des items

    var itemList by remember { mutableStateOf(generateItemList()) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Afficher la TopBar
        TopBar()
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
fun TopBar(modifier: Modifier = Modifier){
// Afficher le rectangle violet en haut de l'écran avec du texte à l'intérieur
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = colorResource(id = R.color.purple_200).copy(alpha = 0.5f)),
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
            IconButton(
                modifier = Modifier.padding(16.dp),
                onClick = { /* TODO */ }
            )
            {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.more_vert), // Assurez-vous de mettre l'icône appropriée
                    contentDescription = stringResource(R.string.more_options),
                    tint = Color.White
                )
            }
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

// Générateur de liste d'items pour la démonstration
fun generateItemList(): List<Item> {
    return List(20) { index -> Item("Item_${index + 1}") }
}

@Composable
fun ListItem(item: Item) {
    Text(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        text = item.name,
        style = TextStyle(fontSize = 24.sp, color = Color.White)
    )
}