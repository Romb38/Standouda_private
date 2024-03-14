package com.example.standouda

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
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

class MainActivity : ComponentActivity() {


    private val unknownSourcesPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (packageManager.canRequestPackageInstalls()) {
                    Log.d("AppManager", "Autorisation d'installer des application inconnues")
                } else {
                    // L'utilisateur n'a pas accordé la permission pour installer des applications de sources inconnues.
                    // Vous pouvez gérer cela en conséquence.
                    Log.e("AppManager", "Interdiction d'installer des application inconnues")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!packageManager.canRequestPackageInstalls()) {
                requestUnknownSourcesPermission()
            } else {
                Log.d("AppManager", "Autorisation d'installer des application inconnues")
            }
        }

        setContent {
            StandoudaTheme {
                Navigation()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Log.d("DEBUG_UPDATE", Constants.IS_INSTALLING.packageName)
        if (!Constants.IS_INSTALLING.isEmpty()) {
            Log.d("DEBUG_UPDATE", "Je passe ici")
            AppDataBase.getDatabase(this).AppDAO().addApp(Constants.IS_INSTALLING)
            //TODO Gerer le loading icon
            gestionApp.refresh(this, false)
            appList = gestionApp.getAppList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestUnknownSourcesPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
            .setData(Uri.parse("package:$packageName"))
        unknownSourcesPermissionLauncher.launch(intent)
    }
}

lateinit var gestionApp: GestionnaireApplication
var appList: List<MyApplication> by mutableStateOf(emptyList())

@Composable
fun Standoudapp(navController: NavController) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val ctx = LocalContext.current

    gestionApp = remember {
        GestionnaireApplication(
            ctx = ctx,
            snackbarHostState = snackbarHostState,
            scope = scope
        )
    }

    appList = remember { gestionApp.getAppList() }

    Log.d("DEBUG_UPDATE", "Fin de l'installation")

    Constants.IS_INSTALLING = remember { MyApplication(packageName = "") }
    Constants.APP_INSTALLED = remember { MyApplication(packageName = "") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Afficher la TopBar
        TopBar(navController = navController)
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            if (gestionApp.getNbApp() != 0) {
                // Afficher la liste d'items avec un espace en haut pour la TopBar
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(appList.size) { index ->
                        ListItem(app = appList[index], snackbarHostState)
                    }
                }
            } else {
                NotAvailable()
            }
            // Bouton de rafraîchissement en bas à droite
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Button(
                    modifier = Modifier
                        .padding(40.dp)
                        .size(80.dp),
                    shape = CircleShape,
                    onClick = {
                        gestionApp.refresh(ctx)
                        appList = gestionApp.getAppList()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_update),
                        contentDescription = "Settings Icon",
                        modifier = Modifier.size(140.dp)
                    )
                }
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }


    }
}


@Composable
fun NotAvailable() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "No apps availables",
            style = TextStyle(fontSize = 16.sp, color = Color.Gray)
        )
    }
}

@Composable
fun TopBar(navController: NavController) {
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
fun MoreOptionButton(navController: NavController) {
    IconButton(
        modifier = Modifier.padding(16.dp),
        onClick = { navController.navigate("settings") }
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


@Composable
fun ListItem(app: MyApplication, snackbarHostState: SnackbarHostState) {
    val ctx = LocalContext.current
    Box(
        modifier = Modifier
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            app.AfficheAppIcon()
            Button(
                onClick = { ouvrirApplicationParPackage(ctx,app.packageName) },
                shape = RoundedCornerShape(0), // Pour rendre les coins carrés
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Column {
                    Text(
                        modifier = Modifier,
                        text = app.name,
                        style = TextStyle(fontSize = 24.sp, color = Color.White)
                    )
                    Text(
                        text = "by " + app.author,
                        style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            app.AffAppInteractButton(snackbarHostState, false)

            val handler = LocalUriHandler.current

            IconButton(
                onClick = { openURL(app.infoLink, handler) },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    painter = painterResource(id = R.drawable.info_24px),
                    contentDescription = "Information",
                    tint = Color.White
                )
            }
        }
    }
    HorizontalDivider(color = Color.Gray)
}


@Preview
@Composable
fun Navigation() {
    val navController = rememberNavController()


    // Utilisez NavHost pour gérer la navigation
    NavHost(navController = navController, startDestination = "main") {
        // Écran principal
        composable("main") {
            Standoudapp(navController)
        }
        // Écran des paramètres
        composable("settings") {
            SettingsScreen(navController = navController)
        }
        //About section
        composable("about") {
            AboutView(navController = navController, name = "About")
        }
    }
}