package com.example.standouda

import android.content.Context

class GestionnaireApplication(
    private var nbApp : Int = 1,
    private val ctx : Context,
    val appDAO: AppDAO = AppDataBase.getDatabase(ctx).AppDAO()
) {

    private val appURLS : List<String> = getAppURLList()
    private val appList : List<MyApplication> = this.generateAppInfosList()

    companion object{
        fun parsingInfo(txt : String) : List<String>{
            // Récupère les informations importantes dans une chaine de caractère d'informations
            val lines = txt.split("\n")
            val parsedList = mutableListOf<String>()

            for (line in lines) {
                val parts = line.split(": ") // On retire ce qui est devant les :
                if (parts.size > 1) { //Si il y a du contenu on le met dans la liste
                    val parsedLine = parts[1].trim()
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

        //[TODO] Récuperer les infos de l'application par son nom danx la base de donnée
        //val actual_app = this.appDAO.getApp(app.name)[0]
        return (true)//actual_app.version == app.version)
    }

    private fun generateAppInfosList(): List<MyApplication> {
        //Liste de sortie
        var list : List<MyApplication> = listOf()

        for (item in this.appURLS){

            val info = getAppInfos(item) //On récupère les information
            val parsedInfos = parsingInfo(info) //On les parses

            val app = MyApplication(
                id = this.nbApp,
                name = parsedInfos[0],
                packageName = parsedInfos[1],
                author = parsedInfos[2],
                version = parsedInfos[3],
                dlLink = parsedInfos[4],
                infoLink = parsedInfos[5]
            ) //On crée une application à partir de ces informations

            //[TODO] Vérifier si l'application existe dans la base de donnée
            val existence = 1//this.appDAO.exists(app.name)

            //On vérifie si l'application est installée
            val isInstalled = app.isInstalled(ctx)

            //On mets à jour l'état de l'application ainsi que la Base de donnée
            if (existence==1) {
                if (!isInstalled){ //Si l'application est desinstallée
                    //[TODO] Retirer l'application de la base de donnée
                    //this.appDAO.removeApp(app) //On retire l'application de la base de donnée
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
                    //[TODO] Ajouter l'application à la base de donnée
                    //this.appDAO.addApp(app)
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

    fun refresh(ctx : Context){
        toast(ctx,"Work in progress")
    }


}