package com.example.standouda

import android.app.Application

class Standouda : Application(){

    override fun onCreate(){
        super.onCreate()

        //[HOTOW] Debug la base de données
        //AppDataBase.getDatabase(this).AppDAO().deleteAll()
    }

}