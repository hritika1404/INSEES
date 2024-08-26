package com.example.insees.BottomSheetDialogDevelopers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.insees.databinding.FragmentRajdeepBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RajdeepFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentRajdeepBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentRajdeepBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gitIcon.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://github.com/Rajdeepsark")
            startActivity(intent)
        }

        binding.linkedInIcon.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://www.linkedin.com/in/rajdeep-sarkar-00b80a244/")
            startActivity(intent)
        }

        binding.instaIcon.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://www.instagram.com/rajdeep_sarkarr?igsh=MzRlODBiNWFlZA==")
            startActivity(intent)
        }

    }

}