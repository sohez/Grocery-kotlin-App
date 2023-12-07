package com.sohez.grocerymaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sohez.grocerymaster.data.db.GroceriesDBInstance
import com.sohez.grocerymaster.databinding.ActivityItemInfoBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityItemInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra("itemId",0)

        val dao = GroceriesDBInstance.getDatabase(applicationContext).getDao()
        var itemCartID:Long = 1
        dao.getItemInfo(id).observe(this){
            itemCartID = it.id
            binding.ItemTitle.text = it.name
            binding.ItemDec.text = it.dec
            binding.ItemPrise.text = "$"+it.prise.toString()
            binding.btnCart.text = if (it.cart){"REMOVE FROM CART"}else{"ADD TO CART"}

            Glide.with(applicationContext)
                .load(it.img)
                .into(binding.productImage)
        }

        binding.btnBuy.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }

        binding.btnCart.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (!dao.isCardAdded(itemCartID)){
                    dao.toggleCart(itemCartID,true)
                }else{
                    dao.toggleCart(itemCartID,false)
                }
            }
        }

    }

}