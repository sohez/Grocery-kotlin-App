package com.sohez.grocerymaster.helper

import android.content.Context
import com.sohez.grocerymaster.data.db.model.ItemAllData
import com.sohez.grocerymaster.data.db.model.ListCategory
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.lang.Exception

class MyHelper {

    fun getCategory(context: Context, fileName : String ): ListCategory? {
        var listtt : ListCategory?
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val reader : Reader = InputStreamReader(inputStream)
            val data = Gson().fromJson(reader, ListCategory::class.java)
            inputStream.close()
            listtt = data
        }catch (e : Exception) {
            listtt = null
        }
        return listtt
    }

    fun getAllData(context: Context, fileName : String ): ItemAllData? {
        var listtt : ItemAllData?
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val reader : Reader = InputStreamReader(inputStream)
            val data = Gson().fromJson(reader,ItemAllData::class.java)
            inputStream.close()
            listtt = data
        }catch (e : Exception) {
            listtt = null
        }
        return listtt
    }
}