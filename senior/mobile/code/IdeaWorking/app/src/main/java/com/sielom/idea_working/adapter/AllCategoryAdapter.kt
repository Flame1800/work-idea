package com.sielom.idea_working.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.model.Category

class AllCategoryAdapter(val inflater: LayoutInflater) :
    ListAdapter<Category, AllCategoryAdapter.CategoryHolder>(object :
        DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.equals(newItem)
        }

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(inflater.inflate(R.layout.item_category_for_select, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleCategoryTextView: TextView =
            itemView.findViewById(R.id.titleCategoryTextView)


        fun bind(category: Category) {
            titleCategoryTextView.text = category.title
        }
    }
}