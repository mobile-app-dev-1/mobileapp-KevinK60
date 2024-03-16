package com.example.myapplication.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGalleryBinding

class Fragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null



    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            contaASiner: ViewGroup?,
            savedInstanceState: Bundle?

    ): View {
        val galleryViewModel =
                ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, contaASiner, false)
        val root: View = binding.root
        val registerButton: Button = binding.RegisterBtn
        registerButton.setOnClickListener {
            findNavController().navigate(R.id.nav_gallery2)
        }


        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

