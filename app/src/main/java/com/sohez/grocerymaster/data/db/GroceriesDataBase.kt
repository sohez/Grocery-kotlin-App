package com.sohez.grocerymaster.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sohez.grocerymaster.data.db.model.ItemAllDataItem

@Database(entities = [ItemAllDataItem::class], version = 1)
abstract class GroceriesDataBase : RoomDatabase() {
    abstract fun getDao(): DataDao
}