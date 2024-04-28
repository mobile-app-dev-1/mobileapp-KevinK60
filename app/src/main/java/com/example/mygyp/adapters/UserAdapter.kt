
package com.example.mygyp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mygyp.databinding.CardPlacemarkBinding
import com.example.mygyp.models.UserModel
import com.squareup.picasso.Picasso

interface UserListener {
    fun onuserClick(user: UserModel)

    /**
     * Called when a user item is clicked.
     *
     * @param user The user model associated with the clicked item.
     */
    fun onUserClick(user: UserModel)

    /**
     * Called when a user item is removed.
     *
     * @param user The user model to be removed.
     */
    fun removeItem(user: UserModel)
}

class UserAdapter constructor(
    private var users: List<UserModel>,
    private val listener: UserListener,
) :
    RecyclerView.Adapter<UserAdapter.MainHolder>() {
        /**
         * Called by RecyclerView when it needs a new ViewHolder instance.
         *
         * This method inflates the layout for the user item using the provided LayoutInflater,
         * creates a new instance of MainHolder with the inflated view binding, and returns it.
         *
         * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
         * @param viewType The type of the new View.
         * @return A new MainHolder instance representing the inflated user item view.
         */
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): MainHolder {
            val binding =
                CardPlacemarkBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            return MainHolder(binding)
        }

        /**
         * Called by RecyclerView to display the data at the specified position.
         *
         * This method retrieves the user at the specified position from the list of users,
         * and then binds the user data to the ViewHolder using the MainHolder's bind method.
         *
         * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position
         * @param position The position of the item within the adapter's data set
         */
        override fun onBindViewHolder(
            holder: MainHolder,
            position: Int,
        ) {
            val user = users[holder.adapterPosition]
            holder.bind(user, listener)
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in the data set.
         */
        override fun getItemCount(): Int = users.size

        /**
         * ViewHolder class for holding and binding user data.
         *
         * @param binding The view binding instance associated with the ViewHolder.
         */
        class MainHolder(private val binding: CardPlacemarkBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(
                user: UserModel,
                listener: UserListener,
            ) {
                binding.placemarkTitle.text = user.firstname
                binding.placemarkTitle.text = user.lastname
                Picasso.get().load(user.image).resize(200, 200).into(binding.imageIcon)
                binding.root.setOnClickListener { listener.onuserClick(user) }
            }
        }
    }
