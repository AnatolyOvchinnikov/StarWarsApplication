package com.app.starwars.ui.lists

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.app.starwars.entity.UserWithMetadata

class UsersAdapter : PagedListAdapter<UserWithMetadata, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as UserViewHolder).bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<UserWithMetadata>() {
            override fun areItemsTheSame(oldItem: UserWithMetadata, newItem: UserWithMetadata): Boolean =
                oldItem.user.userId == newItem.user.userId

            override fun areContentsTheSame(oldItem: UserWithMetadata, newItem: UserWithMetadata): Boolean =
                oldItem.user.name == newItem.user.name && oldItem.visited == newItem.visited
        }
    }
}