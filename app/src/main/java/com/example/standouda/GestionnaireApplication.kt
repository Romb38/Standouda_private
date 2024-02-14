package com.example.standouda

import android.content.Context

class GestionnaireApplication(
    private val nbApp : Int = 4,
) {
    private val appList : List<MyApplication> = this.generateAppList()

    private fun generateAppList(): List<MyApplication> {
        return List(this.nbApp) { index -> MyApplication("${index + 1}") }
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