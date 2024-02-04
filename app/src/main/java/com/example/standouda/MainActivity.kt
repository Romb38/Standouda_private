package com.example.standouda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Preview
@Composable
fun DiceRollerApp(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.more_vert), // Assurez-vous de mettre l'icône appropriée
                        contentDescription = stringResource(R.string.more_options),
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.BottomEnd),
            contentAlignment = Alignment.BottomEnd
        ) {
            RefreshButton(modifier = Modifier.fillMaxSize())
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
        onClick = {/*TODO*/}
    ){
        Icon(
            painter = painterResource(id = R.drawable.outline_update),
            contentDescription = "Settings Icon",
            modifier = Modifier.size(140.dp)
        )
    }

}