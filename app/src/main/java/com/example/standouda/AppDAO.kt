package com.example.standouda

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppDAO {
    @Query("SELECT * FROM AppList")
    fun getAll() : List<MyApplication>

    @Query("SELECT * FROM AppList WHERE packageName = :packageName")
    fun getApp(packageName : String) : List<MyApplication>

    @Insert
    fun addApp(app : MyApplication)

    @Delete
    fun removeApp(app : MyApplication)

    @Query("DELETE FROM APPLIST where packageName = :packageName")
    fun removeAppByPackageName(packageName: String)

    @Query("SELECT COUNT(*) FROM AppList WHERE packageName = :packageName")
    fun exists(packageName : String) : Int

    @Query("UPDATE AppList SET version = :newVersion WHERE packageName = :packageName")
    fun updateVersion(newVersion : String, packageName: String)

    @Query("DELETE FROM AppList")
    fun deleteAll()
}