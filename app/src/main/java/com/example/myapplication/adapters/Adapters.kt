////import android.view.LayoutInflater
////import android.view.ViewGroup
////import androidx.recyclerview.widget.RecyclerView
////import com.example.myapplication.models.UserModel
////import com.squareup.picasso.Picasso
////
////interface UserListener  {
////    fun onUserClick(user: UserModel)
////}
////
////class UserAdapter  constructor(
////    private var users: List<UserModel>,
////    private val listener: UserListener ) :
////    RecyclerView.Adapter<UserAdapter.MainHolder>()
////{
////
////
////
////    override fun onBindViewHolder(holder: MainHolder, position: Int) {
////        val user = users[holder.adapterPosition]
////        holder.bind(user, listener)
////    }
////
////    override fun getItemCount(): Int = users.size
////
////    class MainHolder(private val binding : CardPlacemarkBinding) :
////        RecyclerView.ViewHolder(binding.root) {
////
////        fun bind(user: UserModel, listener: UserListener) {
////            binding.apply {
////                user.firstname = user.firstname
////                user.lastname = user.lastname
////                Picasso.get().load(user.image).resize(200, 200).into(imageIcon)
////                root.setOnClickListener { listener.onUserClick(user) }
////            }
////        }
////    }
//}