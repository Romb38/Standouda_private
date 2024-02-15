package com.example.standouda

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppDAO {
    @Query("SELECT * FROM AppList")
    fun getAll() : List<MyApplication>

    @Query("SELECT * FROM AppList WHERE name = :name")
    fun getApp(name : String) : List<MyApplication>

    @Insert
    fun addApp(app : MyApplication)

    @Delete
    fun removeApp(app : MyApplication)

    @Query("SELECT COUNT(*) FROM AppList WHERE name = :name")
    fun exists(name : String) : Int

    @Query("DELETE FROM AppList")
    fun deleteAll()
}