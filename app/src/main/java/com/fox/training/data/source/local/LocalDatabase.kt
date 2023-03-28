package com.fox.training.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.fox.training.data.network.response.Music
import com.fox.training.util.AppConstants


@Database(entities = [Music::class], version = AppConstants.DATABASE_VERSION_1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDao

    companion object {
        private var INSTANCE: LocalDatabase? = null
        fun getInstance(context: Context): LocalDatabase {
            if (INSTANCE == null) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    AppConstants.DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }
            return INSTANCE as LocalDatabase
        }
    }
}