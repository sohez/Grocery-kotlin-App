package com.sohez.grocerymaster.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohez.grocerymaster.ItemInfoActivity
import com.sohez.grocerymaster.data.db.model.ItemAllDataItem
import com.sohez.grocerymaster.databinding.CategoryRowItemsBinding
import com.bumptech.glide.RequestManager
import kotlin.random.Random

class DiscountAdapter(private val glide:RequestManager,private val context: Context) : ListAdapter<ItemAllDataItem,DiscountAdapter.ViewHolder>(DifCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountAdapter.ViewHolder {
        val binding = CategoryRowItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscountAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: CategoryRowItemsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item : ItemAllDataItem){
            glide.load(item.img)
                .into(binding.categoryImage)
            val random = Random.nextInt(10, 15)
            binding.textView5.text = "$random% Dicount"

            binding.root.setOnClickListener {
                val i = Intent(context, ItemInfoActivity::class.java)
                i.putExtra("itemId", item.id)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(i)
            }
        }
    }

    private class DifCallBack : DiffUtil.ItemCallback<ItemAllDataItem>(){
        override fun areItemsTheSame(oldItem: ItemAllDataItem, newItem: ItemAllDataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemAllDataItem, newItem: ItemAllDataItem): Boolean {
            return oldItem == newItem

        }

    }

}