package com.sohez.grocerymaster.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sohez.grocerymaster.data.db.model.ItemAllDataItem


@Dao
interface DataDao {

    @Insert
    suspend fun insertData(categoryData: ItemAllDataItem)

    @Query("SELECT * FROM Items")
    fun getAllData(): LiveData<List<ItemAllDataItem>>

    @Query("SELECT * FROM Items WHERE dicount = 1")
    fun getDiscount(): LiveData<List<ItemAllDataItem>>

    @Query("SELECT * FROM Items WHERE id = :id")
    fun getItemInfo(id: Long): LiveData<ItemAllDataItem>
//
//
    @Query("SELECT * FROM Items WHERE cart = 1")
    fun getCartItems(): LiveData<List<ItemAllDataItem>>
//
    @Query("SELECT * FROM Items WHERE category = :category")
    fun getCategories(category : String): LiveData<List<ItemAllDataItem>>
//
    @Query("SELECT * FROM Items WHERE id BETWEEN 1 AND 4")
    fun getRecently(): LiveData<List<ItemAllDataItem>>

    @Query("UPDATE Items SET cart = :value WHERE id = :id")
    suspend fun toggleCart(id: Long, value: Boolean)

    @Query("SELECT cart FROM Items WHERE id = :id")
    suspend fun isCardAdded(id: Long): Boolean

    @Query("SELECT * FROM Items WHERE name Like '%' || :name || '%'")
    fun sortCategory(name: String): LiveData<List<ItemAllDataItem>>

    @Query("SELECT COUNT(*) FROM Items")
    suspend fun getLength(): Int
}