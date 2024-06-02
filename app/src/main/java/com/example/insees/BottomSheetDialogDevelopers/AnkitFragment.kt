package com.example.insees.BottomSheetDialogDevelopers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.insees.R
import com.example.insees.databinding.FragmentAnkitBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AnkitFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentAnkitBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAnkitBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gitIcon.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://github.com/AnKiTk203")
            startActivity(intent)
        }

        binding.linkedInIcon.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://www.linkedin.com/in/ankit-kumar-barnwal-138a5a257/")
            startActivity(intent)
        }

        binding.instaIcon.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://www.instagram.com/ankit_2k3/")
            startActivity(intent)
        }

    }

}