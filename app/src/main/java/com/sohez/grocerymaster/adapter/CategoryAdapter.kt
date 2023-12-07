package com.sohez.grocerymaster.adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sohez.grocerymaster.LIstItemViewActivity
import com.sohez.grocerymaster.data.db.model.Category
import com.sohez.grocerymaster.databinding.CategoryRowItemsBinding
import com.bumptech.glide.RequestManager

class CategoryAdapter(private val glide:RequestManager,private val context: Context) : ListAdapter<Category,CategoryAdapter.ViewHolder>(DifCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val binding = CategoryRowItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: CategoryRowItemsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item : Category){
            glide.load(item.img)
                .into(binding.categoryImage)
            binding.textView5.text = item.category

            binding.root.setOnClickListener {
                val i = Intent(context, LIstItemViewActivity::class.java)
                i.putExtra("category", item.category)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(i)
            }
        }
    }

    private class DifCallBack : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem

        }

    }

}





