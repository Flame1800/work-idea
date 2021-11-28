package com.sielom.idea_working.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.model.User

class SpecialistAdapter(val inflater: LayoutInflater) :
    ListAdapter<User, SpecialistAdapter.SpecialistHolder>(object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }
    }) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecialistAdapter.SpecialistHolder {
        return SpecialistHolder(inflater.inflate(R.layout.item_specialist, parent, false))
    }

    override fun onBindViewHolder(holder: SpecialistHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class SpecialistHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val countProjectTextView: TextView =
            itemView.findViewById(R.id.countProjectTextView)
        private val directionsTextView: TextView = itemView.findViewById(R.id.directionsTextView)
//        private val openProfileButton: TextView = itemView.findViewById(R.id.openProfileButton)

        fun bind(user: User) {
            nameTextView.text = user.username
            countProjectTextView.text = "Проекты: " + (user.countProjects ?: 0).toString()
            user.categories?.let {
                var directions = ""
                for(direction in it) {
                    directions += direction.title + "; "
                }

                directionsTextView.text = directions
            }
        }
    }
}