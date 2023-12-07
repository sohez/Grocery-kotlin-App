package com.sohez.grocerymaster.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sohez.grocerymaster.data.db.model.ItemAllDataItem
import com.sohez.grocerymaster.databinding.DiscountedRowItemsBinding

class CardAdapter : ListAdapter<ItemAllDataItem,CardAdapter.ViewHolder>(DifCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        val binding = DiscountedRowItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: DiscountedRowItemsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item : ItemAllDataItem){
          //  binding.discountImage.setImageResource(item.catiImg)
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
