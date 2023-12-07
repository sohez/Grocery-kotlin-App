package com.sohez.grocerymaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.sohez.grocerymaster.adapter.CartItemCallBack
import com.sohez.grocerymaster.adapter.RecentlyViewedAdapter
import com.sohez.grocerymaster.data.db.DataDao
import com.sohez.grocerymaster.data.db.GroceriesDBInstance
import com.sohez.grocerymaster.databinding.ActivityListItemViewBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LIstItemViewActivity : AppCompatActivity(),CartItemCallBack{

    private lateinit var binding : ActivityListItemViewBinding

    private lateinit var dao : DataDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListItemViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = GroceriesDBInstance.getDatabase(applicationContext).getDao()
        val recentlyViewedAdapter = RecentlyViewedAdapter(Glide.with(applicationContext),this,applicationContext)
        binding.recentlyLayout.layoutManager = GridLayoutManager(this,2)
        binding.recentlyLayout.adapter = recentlyViewedAdapter

        val owner : LifecycleOwner = this

        if(intent.hasExtra("category")) {
            binding.editText.visibility = View.GONE
            val category = intent.getStringExtra("category")
            dao.getCategories(category!!).observe(this) {
                recentlyViewedAdapter.submitList(it)
            }
        }else{
            binding.editText.visibility = View.VISIBLE
            binding.editText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                   dao.sortCategory(p0.toString()).observe(owner){
                       recentlyViewedAdapter.submitList(it)
                   }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
        }


    }

    override fun addToCart(itemId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            if (!dao.isCardAdded(itemId)){
                dao.toggleCart(itemId,true)
            }else{
                dao.toggleCart(itemId,false)
            }
        }
    }

}