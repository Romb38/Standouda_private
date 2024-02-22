package com.example.standouda

import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class GestionnaireApplication(
    private var nbApp : Int = 1,
    private val ctx : Context,
    private val appDAO: AppDAO = AppDataBase.getDatabase(ctx).AppDAO(),
    private val snackbarHostState: SnackbarHostState,
    private val scope : CoroutineScope,
) {


    private var appURLS : List<String> = safeGetAppUrlList()
    private var appList : List<MyApplication> = this.generateAppInfosList()
    private val downloadReceiver = MyBroadcastReceiver(snackbarHostState,ctx)

    private val errorMessage = "Check internet connexion"

    init {
        setUpBroadCastReceiver()
    }


    private fun setUpBroadCastReceiver(){
        // Enregistrez le BroadcastReceiver pour les actions appropriées
        val filter = IntentFilter().apply {
            addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        }
        registerReceiver(ctx,downloadReceiver, filter,RECEIVER_EXPORTED)
    }

    private fun safeGetAppUrlList() : List<String> {
        val net = isNetworkAvailable(ctx)

        return if (net) {
            getAppURLList()
        } else {
            scope.launch {
                snackbarHostState.showSnackbar(errorMessage)
            }
            listOf()
        }
    }
    companion object{
        fun parsingInfo(txt : String) : List<String>{
            //[TODO] Rendre le parsing moins "fort"
            // Récupère les informations importantes dans une chaine de caractère d'informations
            val lines = txt.split("\n")
            val parsedList = mutableListOf<String>()

            for (line in lines) {
                val parts = line.split(": ") // On retire ce qui est devant les :
                if (parts.size > 1) { //Si il y a du contenu on le met dans la liste
                    val parsedLine = parts[1].trim()
                    Log.d("parsing",parsedLine)
                    parsedList.add(parsedLine)
                } else {
                    //Sinon on rentre simplement vide
                    parsedList.add("")
                }
            }
            return parsedList
        }
    }

    private fun checkVersion(app : MyApplication) : Boolean {
        //Vérifie la version de l'application installée (dans la BDD) comparée aux infos recues

        val actualApp = this.appDAO.getApp(app.packageName)[0]
        return (actualApp.version == app.version) //(true)
    }

    private fun generateAppInfosList(): List<MyApplication> {

        //Liste de sortie
        var list : List<MyApplication> = listOf()
        val net = isNetworkAvailable(ctx)

        //Si il y a des URLS dans la liste mais qu'il n'y a pas de connexion internet
        if(!net && list != emptyList<MyApplication>()){
            scope.launch {
                snackbarHostState.showSnackbar(errorMessage)
            }
            return listOf()
        }

        Log.d("getAppList",this.appURLS.toString())

        for (item in this.appURLS){

            val info = getAppInfos(item) //On récupère les information
            val parsedInfos = parsingInfo(info) //On les parses

            val app = MyApplication(
                name = parsedInfos[0],
                packageName = parsedInfos[1],
                author = parsedInfos[2],
                version = parsedInfos[3],
                icon = parsedInfos[4],
                dlLink = parsedInfos[5],
                infoLink = parsedInfos[6]
            ) //On crée une application à partir de ces informations

            val existence = this.appDAO.exists(app.packageName) //1

            //On vérifie si l'application est installée
            val isInstalled = app.isInstalled(ctx)

            Log.d("checkApp",isInstalled.toString() + " ${app.packageName }")
            Log.d("checkApp","$existence")

            //On mets à jour l'état de l'application ainsi que la Base de donnée
            if (existence == 1) {
                if (!isInstalled){ //Si l'application est desinstallée
                    this.appDAO.removeAppByPackageName(app.packageName) //On retire l'application de la base de donnée
                    app.state = ApplicationState.UNINSTALLED

                } else { //L'application est installée
                    if(this.checkVersion(app)){ //Si elle à la même version que celle en ligne
                        app.state = ApplicationState.INSTALLED
                    } else { //Sinon on peut la mettre a jour
                        app.state = ApplicationState.UPDATABLE
                    }
                }
            } else {
                //Si l'application n'est pas dans la base de donnée
                if (isInstalled){ // Soit elle est installée dans ce cas, on l'ajoute
                    this.appDAO.addApp(app)
                    app.state = ApplicationState.INSTALLED
                } else { // Soi elle n'est pas installée
                    app.state = ApplicationState.UNINSTALLED
                }
            }

            //On ajoute l'application à la liste
            list = list.plus(app)
            this.nbApp ++

        }
        return list
    }

    fun getAppList() : List<MyApplication> {
        return this.appList
    }

    fun getNbApp() : Int{
        return this.nbApp
    }

    fun refresh(ctx : Context, affCallback : Boolean = true){
        //Rafraichis la liste d'application sur la page centrale
        val net = isNetworkAvailable(ctx)

        if(net) { //Si on a de la connexion
            this.appURLS = getAppURLList()
            this.appList = this.generateAppInfosList()
            Log.d("OnRefresh", this.appList.toString())
            Log.d("OnRefresh", this.appURLS.toString())

            if (affCallback) {
                scope.launch {
                    snackbarHostState.showSnackbar("Refreshed")
                }
            }
        } else {
            scope.launch {
                snackbarHostState.showSnackbar(errorMessage)
            }
        }
    }


}