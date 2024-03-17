class UserCreationFragment : Fragment() {

    private var _binding: FragmentCreateruserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateruserBinding.inflate(inflater, container, false)
        val root: View = binding.root



        binding.btnAddUser.setOnClickListener {
            val user = UserModel().apply {
                firstname = binding.firstnameid.text.toString()
                lastname = binding.lastnameid.text.toString()
            }

            if (user.firstname.isNotEmpty()) {
                val mainActivity = requireActivity() as? MainActivity
                mainActivity?.let {
                    if (edit) {
                        it.updateUser(user.copy())
                    } else {
                        it.createUser(user.copy())
                        i(user.toString())
                    }
                    it.setResult(Activity.RESULT_OK)
                    it.finish()
                }
            } else {
                // Handle the case where firstname is empty
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
