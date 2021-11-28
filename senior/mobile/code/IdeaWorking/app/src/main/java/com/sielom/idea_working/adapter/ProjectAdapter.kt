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
import com.sielom.idea_working.model.Project

class ProjectAdapter(val inflater: LayoutInflater) :
    ListAdapter<Project, ProjectAdapter.ProjectHolder>(object : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.author == newItem.author &&
                    oldItem.users == newItem.users &&
                    oldItem.description == newItem.description &&
                    oldItem.title == newItem.title
        }
    }) {

    private var onCLickListener: ProjectOnCLickListener? = null

    interface ProjectOnCLickListener {
        fun onEnterProjectButtonClick(projectId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(inflater.inflate(R.layout.item_project, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun setOnClickListener(onClickListener: ProjectOnCLickListener) {
        this.onCLickListener = onClickListener
    }

    inner class ProjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameAuthorTextView: TextView = itemView.findViewById(R.id.nameAuthorTextView)
        private val titleProjectTextView: TextView =
            itemView.findViewById(R.id.titleProjectTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val countUsersTextView: TextView = itemView.findViewById(R.id.countUsersTextView)
        private val countCommentTextView: TextView =
            itemView.findViewById(R.id.countCommentTextView)
        private val enterProjectButton: Button = itemView.findViewById(R.id.enterProjectButton)

        fun bind(project: Project) {
            nameAuthorTextView.text = project.author?.username ?: "Неизвестен"
            titleProjectTextView.text = project.title
            descriptionTextView.text = project.description
            countUsersTextView.text = (project.participants?.size ?: 0).toString()
            countCommentTextView.text = (project.comments?.size ?: 0).toString()

            if (onCLickListener == null) {
                enterProjectButton.visibility = View.INVISIBLE
            }

            enterProjectButton.setOnClickListener {
                onCLickListener?.onEnterProjectButtonClick(project.id)
            }
        }
    }
}