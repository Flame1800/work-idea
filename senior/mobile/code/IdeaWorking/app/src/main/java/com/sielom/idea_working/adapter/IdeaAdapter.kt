package com.sielom.idea_working.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.model.Idea

class IdeaAdapter(val inflater: LayoutInflater) :
    ListAdapter<Idea, IdeaAdapter.IdeaHolder>(object : DiffUtil.ItemCallback<Idea>() {
        override fun areItemsTheSame(oldItem: Idea, newItem: Idea): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Idea, newItem: Idea): Boolean {
            return oldItem.equals(newItem)
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeaHolder {
        return IdeaHolder(inflater.inflate(R.layout.item_project, parent, false))
    }

    override fun onBindViewHolder(holder: IdeaHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class IdeaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameAuthorTextView: TextView = itemView.findViewById(R.id.nameAuthorTextView)
        private val titleProjectTextView: TextView =
            itemView.findViewById(R.id.titleProjectTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val countUsersTextView: TextView = itemView.findViewById(R.id.countUsersTextView)
        private val countCommentTextView: TextView =
            itemView.findViewById(R.id.countCommentTextView)

        private val enterProjectButton: Button =
            itemView.findViewById(R.id.enterProjectButton)

        fun bind(idea: Idea) {
            nameAuthorTextView.text = idea.user?.username ?: "Неизвестен"
            titleProjectTextView.text = idea.title
            descriptionTextView.text = idea.description
            countUsersTextView.text = "1"
            countCommentTextView.text = "0"
            enterProjectButton.visibility = View.INVISIBLE
        }
    }
}