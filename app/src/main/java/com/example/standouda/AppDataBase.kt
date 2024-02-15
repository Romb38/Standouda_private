package com.example.standouda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyApplication::class], version = AppDataBase.VERSION)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        const val VERSION = 1
        fun getDatabase(ctx : Context): AppDataBase{
            return Room.databaseBuilder(ctx,AppDataBase::class.java,"AppListDatabase").build()
        }
    }

    abstract fun AppDAO() : AppDAO

}