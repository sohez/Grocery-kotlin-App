package com.sohez.grocerymaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sohez.grocerymaster.data.db.GroceriesDBInstance
import com.sohez.grocerymaster.helper.MyHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = GroceriesDBInstance.getDatabase(applicationContext)
        val dao = database.getDao()

        CoroutineScope(Dispatchers.IO).launch {
            val allData = MyHelper().getAllData(applicationContext,"items.json")
                if(dao.getLength() == 0) {
                    if (allData != null) {
                        for (i in allData)
                            dao.insertData(i)
                    }
            }
            runOnUiThread {
                changeActivity()
            }
        }
    }

    private fun changeActivity(){
        startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }
}