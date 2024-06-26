package com.example.standouda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun HorizontalDivider(modifier: Modifier = Modifier, thickness: Dp = 2.dp, color: Color = Color.White) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color)
            .height(thickness),
    )
    {}
}

@Composable
fun Bar(name : String, toGo : String,navController: NavController){
// Afficher le rectangle violet en haut de l'écran avec du texte à l'intérieur
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
        ) {

            IconButton(
                modifier = Modifier.padding(16.dp),
                onClick = { navController.navigate(toGo) }
            ) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    painter = painterResource(id = R.drawable.arrow_back_24px), // Assurez-vous de mettre l'icône appropriée
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }

            Text(
                modifier = Modifier.padding(16.dp),
                text = name,
                style = TextStyle(fontSize = 24.sp, color = Color.White),
            )
        }
    }
    HorizontalDivider()
}
