package ie.setu.placemark.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.placemark.databinding.CardPlacemarkBinding
import com.example.myapplication.models.User
import com.example.myapplication.models.UsersStore

interface PlacemarkListener {
    fun onPlacemarkClick(user: UsersStore)
}

class PlacemarkAdapter constructor(private var users: List<UsersStore>,
                                   private val listener: PlacemarkListener) :
    RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlacemarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = users[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = users.size

    class MainHolder(private val binding : CardPlacemarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UsersStore, listener: PlacemarkListener) {
            binding.placemarkTitle.text = user.title
            binding.placemarkDescription.text = placemark.description
            Picasso.get().load(user.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onPlacemarkClick(user)}
        }
    }
}