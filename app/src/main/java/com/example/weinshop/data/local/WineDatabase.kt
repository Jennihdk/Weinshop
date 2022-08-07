package com.example.weinshop.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weinshop.data.models.ShoppingCart
import com.example.weinshop.data.models.Wine

@Database(entities = [Wine::class, ShoppingCart::class], version = 1)
abstract class WineDatabase : RoomDatabase() {

    /** Variable für das Interface aus der WineDao */
    abstract val wineDao: WineDatabaseDao

    companion object {
        /** Speichert die Instance der WineDatabase um mit dieser arbeiten zu können */
        private lateinit var INSTANCE: WineDatabase

        /** Erstellt eine neue Datenbank, wenn noch keine vorhanden ist*/
        fun getDatabase(context: Context): WineDatabase {
            synchronized(WineDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WineDatabase::class.java,
                        "wine_database"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}


