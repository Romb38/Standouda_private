package com.example.standouda

object Constants {
    val SETTINGS_MENU = listOf(
        "About",
    )

    val PACKAGE_NAME = "com.example.standouda"

    val SETTINGS_MENU_ICON = mapOf(
        "About" to R.drawable.info_24px,
        "default" to R.drawable.icon_default
    )

    const val GITHUB_PROFIL_LINK = "https://github.com/Romb38"

    //Prendre le github de Standouda public et rajouter le fichier
    var GITHUB_APP_LINK = "https://raw.githubusercontent.com/Romb38/StandoudApp/main/"

    var IS_INSTALLING = MyApplication(packageName = "")
    var APP_INSTALLED = MyApplication(packageName = "")
}
