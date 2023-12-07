package com.sohez.grocerymaster.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Items")
data class ItemAllDataItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val cart: Boolean,
    val category: String,
    val dec: String,
    val dicount: Boolean,
    val img: String,
    val name: String,
    val prise: Int,
    val qty: Int
)