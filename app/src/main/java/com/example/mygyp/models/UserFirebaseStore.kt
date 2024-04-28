//import com.example.mygyp.models.UserModel
//import com.example.mygyp.models.UserStore
//import com.google.firebase.firestore.FirebaseFirestore
//import android.net.Uri
//import timber.log.Timber
//import kotlin.coroutines.resume
//import kotlin.coroutines.resumeWithException
//import kotlin.coroutines.suspendCoroutine
//
//class UserFirebaseStore : UserStore {
//
//    private val db = FirebaseFirestore.getInstance()
//    private val userDocuments = db.collection("users")
//
//    // Make findAll a suspend function
//    suspend override fun findAll(): List<UserModel> = suspendCoroutine { continuation ->
//        val usersList = mutableListOf<UserModel>()
//
//        userDocuments.get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    usersList.add(
//                        UserModel(
//                            id = (document.data["id"] as? Long) ?: 0L,
//                            firstname = document.data["firstname"] as? String ?: "",
//                            lastname = document.data["lastname"] as? String ?: "",
//                            image = Uri.parse(document.data["image"] as? String ?: "")
//                        )
//                    )
//                }
//                continuation.resume(usersList)
//            }
//            .addOnFailureListener { exception ->
//                Timber.e("Error fetching users: $exception")
//                continuation.resumeWithException(exception)
//            }
//    }
//
//    override fun create(user: UserModel) {
//        val data = mutableMapOf<String, Any>().apply {
//            put("id", user.id)
//            put("firstname", user.firstname)
//            put("lastname", user.lastname)
//            put("image", user.image.toString()) // Store the Uri as a String
//        }
//
//        userDocuments
//            .add(data)
//            .addOnSuccessListener { documentReference ->
//                Timber.i("User added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Timber.e("Error adding user: $e")
//            }
//    }
//
//    // Make update a suspend function
//    suspend override fun update(user: UserModel) {
//        val userReference = userDocuments.document(user.id.toString())
//        userReference.set(user)
//            .addOnSuccessListener {
//                Timber.i("User updated successfully")
//            }
//            .addOnFailureListener { e ->
//                Timber.e("Error updating user: $e")
//            }
//    }
//
//    suspend override fun findbyId(){}
//    override fun delete(user: UserModel) {
//        val userReference = userDocuments.document(user.id.toString())
//        userReference.delete()
//            .addOnSuccessListener {
//                Timber.i("User deleted successfully")
//            }
//            .addOnFailureListener { e ->
//                Timber.e("Error deleting user: $e")
//            }
//    }
//}
