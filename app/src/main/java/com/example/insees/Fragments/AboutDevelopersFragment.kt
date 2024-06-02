package com.example.insees.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.insees.BottomSheetDialogDevelopers.AnkitFragment
import com.example.insees.BottomSheetDialogDevelopers.BishalFragment
import com.example.insees.BottomSheetDialogDevelopers.RishiFragment
import com.example.insees.BottomSheetDialogDevelopers.SudipFragment
import com.example.insees.databinding.FragmentAboutDevelopersBinding

class AboutDevelopersFragment : Fragment() {

    private lateinit var binding: FragmentAboutDevelopersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentAboutDevelopersBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetSudip=SudipFragment()
        val bottomSheetAnkit=AnkitFragment()
        val bottomSheetRishi=RishiFragment()
        val bottomsheetBishal= BishalFragment()

        binding.btnSudip.setOnClickListener {
            bottomSheetSudip.show(childFragmentManager,"BottomSheetDialog")
        }

        binding.btnAnkit.setOnClickListener {
            bottomSheetAnkit.show(childFragmentManager,"BottomSheetDialog")
        }

        binding.btnRishi.setOnClickListener {
            bottomSheetRishi.show(childFragmentManager,"BottomSheetDialog")
        }

        binding.btnBishal.setOnClickListener {
            bottomsheetBishal.show(childFragmentManager,"BottomSheetDialog")
        }

    }

}