package com.sohez.grocerymaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.sohez.grocerymaster.adapter.CartItemCallBack
import com.sohez.grocerymaster.adapter.RecentlyViewedAdapter
import com.sohez.grocerymaster.data.db.DataDao
import com.sohez.grocerymaster.data.db.GroceriesDBInstance
import com.sohez.grocerymaster.databinding.ActivityCartBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity(),CartItemCallBack {

    private lateinit var binding : ActivityCartBinding
    private lateinit var dao : DataDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val glide = Glide.with(applicationContext)
        val adapter = RecentlyViewedAdapter(glide,this,applicationContext)
        binding.recentlyLayout.layoutManager = GridLayoutManager(this,2)
        binding.recentlyLayout.adapter = adapter

        dao = GroceriesDBInstance.getDatabase(applicationContext).getDao()

        dao.getCartItems().observe(this){
            if(!it.isNullOrEmpty()){
                var total = 0
                for(i in it){
                    total += i.prise
                }
                binding.buyBtn.text = "Buy Now $$total"
                adapter.submitList(it)
            }else{
                adapter.submitList(emptyList())
                binding.buyBtn.text = "Empty Cart"
            }
        }
    }

    override fun addToCart(itemId: Long) {
        CoroutineScope(Dispatchers.IO).launch{
            if(!dao.isCardAdded(itemId)) {
                dao.toggleCart(itemId, true)
            }else{
                dao.toggleCart(itemId,false)
            }
        }
    }
}