package com.sohez.grocerymaster.data.db

import android.content.Context
import androidx.room.Room


object GroceriesDBInstance {

        private var database: GroceriesDataBase? = null
        fun getDatabase(context: Context): GroceriesDataBase {
            return database ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroceriesDataBase::class.java,
                    "GroceriesDataBase"
                ).build()
                database = instance
                instance
            }
        }
}