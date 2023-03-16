package com.fox.training.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.fox.training.data.network.response.Music
import com.fox.training.util.AppConstants


@Database(entities = [Music::class], version = AppConstants.DATABASE_VERSION)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun LocalDao(): LocalDao

    companion object {
        private var instance: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            if (instance == null) {
                val mInstance = databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    AppConstants.DATABASE_NAME
                ).build()
                instance = mInstance
            }
            return instance as LocalDatabase
        }
    }

}