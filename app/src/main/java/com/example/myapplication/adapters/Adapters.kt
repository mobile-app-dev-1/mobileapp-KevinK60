package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.UserModel


interface UserListener {
    fun onUserClick(user: UserModel)
}

class UserAdapter(
    private var users: List<UserModel>,
    private val listener: UserListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, listener)
    }

    override fun getItemCount(): Int = users.size

    fun setData(newUsers: List<UserModel>) {
        users = newUsers
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.userName)

        fun bind(user: UserModel, listener: UserListener) {
            userNameTextView.text = "${user.firstname} ${user.lastname}"

            itemView.setOnClickListener { listener.onUserClick(user) }
        }
    }
}
