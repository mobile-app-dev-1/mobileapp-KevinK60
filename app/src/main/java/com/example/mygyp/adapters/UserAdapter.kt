package com.example.mygyp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mygyp.databinding.CardPlacemarkBinding
import com.example.mygyp.models.UserModel
import com.squareup.picasso.Picasso

interface UserListener {
    fun onuserClick(user: UserModel)
    fun onUserClick(user: UserModel)
    fun removeItem(user: UserModel)
}

class UserAdapter constructor(private var users: List<UserModel>,
                                   private val listener: UserListener) :
    RecyclerView.Adapter<UserAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlacemarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = users[holder.adapterPosition]
        holder.bind(user, listener)
    }

    override fun getItemCount(): Int = users.size

    class MainHolder(private val binding : CardPlacemarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserModel, listener: UserListener) {
            binding.placemarkTitle.text = user.firstname
            binding.placemarkTitle.text = user.lastname
            Picasso.get().load(user.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onuserClick(user)}
        }
    }

}