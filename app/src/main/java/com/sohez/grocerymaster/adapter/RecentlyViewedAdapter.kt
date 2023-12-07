package com.sohez.grocerymaster.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sohez.grocerymaster.ItemInfoActivity
import com.sohez.grocerymaster.R
import com.sohez.grocerymaster.data.db.model.ItemAllDataItem
import com.sohez.grocerymaster.databinding.RecentlyViewedItemsBinding
import com.bumptech.glide.RequestManager

class RecentlyViewedAdapter(
    private val glide: RequestManager,
    private val cartItemCallBack: CartItemCallBack,
    private val context: Context
) : ListAdapter<ItemAllDataItem, RecentlyViewedAdapter.ViewHolder>(DifCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentlyViewedAdapter.ViewHolder {
        val binding =
            RecentlyViewedItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentlyViewedAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: RecentlyViewedItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemAllDataItem) {
            glide.load(item.img)
                .into(binding.imageView4)
            binding.price.text = "$" + item.prise.toString()
            binding.description.text = "#Hot"
            binding.productName.text = item.name.toString()
            binding.recentlyLayout.setBackgroundResource(R.drawable.bg_cornerr)
            binding.unit.visibility = View.GONE

            if(item.cart){
                binding.imageViewCart.setImageResource(R.drawable.ic_trash)
            }else{
                binding.imageViewCart.setImageResource(R.drawable.ic_cart)
            }

            binding.imageViewCart.setOnClickListener {
                cartItemCallBack.addToCart(item.id)
            }
            binding.recentlyLayout.setOnClickListener {
                val i = Intent(context, ItemInfoActivity::class.java)
                i.putExtra("itemId", item.id)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(i)
            }
        }
    }

    private class DifCallBack : DiffUtil.ItemCallback<ItemAllDataItem>() {
        override fun areItemsTheSame(oldItem: ItemAllDataItem, newItem: ItemAllDataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemAllDataItem,
            newItem: ItemAllDataItem
        ): Boolean {
            return oldItem == newItem

        }

    }

}