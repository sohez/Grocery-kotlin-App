package com.sohez.grocerymaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sohez.grocerymaster.adapter.CartItemCallBack
import com.sohez.grocerymaster.adapter.CategoryAdapter
import com.sohez.grocerymaster.adapter.DiscountAdapter
import com.sohez.grocerymaster.adapter.RecentlyViewedAdapter
import com.sohez.grocerymaster.data.db.DataDao
import com.sohez.grocerymaster.data.db.GroceriesDBInstance
import com.sohez.grocerymaster.databinding.ActivityHomeBinding
import com.sohez.grocerymaster.helper.MyHelper
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), CartItemCallBack{

    private lateinit var binding : ActivityHomeBinding
    private lateinit var dao: DataDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //dao define
        val db = GroceriesDBInstance.getDatabase(this)
        dao = db.getDao()
        val category = MyHelper().getCategory(applicationContext,"categories.json")
        //Categorys

        val glide = Glide.with(applicationContext)
        val categoryAdapter = CategoryAdapter(glide,applicationContext)
        binding.categoryRecycler.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        binding.categoryRecycler.adapter = categoryAdapter
        categoryAdapter.submitList(category)

        //Discount
        val discountAdapter = DiscountAdapter(glide,applicationContext)
        binding.discountedRecycler.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        binding.discountedRecycler.adapter = discountAdapter
        dao.getDiscount().observe(this){
            discountAdapter.submitList(it)
        }

        //Recently
        val adapter2 = RecentlyViewedAdapter(glide,this,applicationContext)
        binding.recentlyItem.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        binding.recentlyItem.adapter = adapter2
        dao.getRecently().observe(this){
            adapter2.submitList(it)
        }

        binding.editText.setOnClickListener {
            startActivity(Intent(this,LIstItemViewActivity::class.java))
        }
        binding.imageView.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
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