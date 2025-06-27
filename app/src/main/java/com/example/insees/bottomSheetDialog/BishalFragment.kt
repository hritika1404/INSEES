package com.example.insees.bottomSheetDialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.insees.databinding.FragmentBishalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * A simple [Fragment] subclass.
 * Use the [BishalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BishalFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentBishalBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBishalBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gitIcon.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://github.com/bishalpb2004")
            startActivity(intent)
        }

        binding.linkedInIcon.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://www.linkedin.com/in/bishal-paul-biswas-592341257/")
            startActivity(intent)
        }

        binding.instaIcon.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://www.instagram.com/bishalbiswas2004/")
            startActivity(intent)
        }


    }
}