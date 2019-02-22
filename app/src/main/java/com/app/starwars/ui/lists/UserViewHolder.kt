package com.app.starwars.ui.lists

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.app.starwars.R
import com.app.starwars.databinding.ItemUserLayoutBinding
import com.app.starwars.entity.UserWithMetadata

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var user: UserWithMetadata? = null

    init {
        view.setOnClickListener {
            val directions = user?.let { user ->
                PersonsListFragmentDirections.actionPersonsListFragmentToUserDetailsFragment(user.user)
            }
            directions?.let { it ->
                view.findNavController().navigate(it)
            }
        }
    }

    var userObservable = ObservableField<UserWithMetadata>()

    fun bind(user: UserWithMetadata?) {
        if (user != null) {
            userObservable.set(user)
            this.user = user
            val resources = itemView.resources
//            itemView.user_name.text = user.user.name
        } else {

        }
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_layout, parent, false)

            val binding = DataBindingUtil.bind<ItemUserLayoutBinding>(view)
            val userViewHolder = UserViewHolder(view)
            binding?.viewholder = userViewHolder

            return binding?.viewholder!!
//            return UserViewHolder(view)
        }
    }
}