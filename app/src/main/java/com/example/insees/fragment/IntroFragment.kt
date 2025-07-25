package com.example.insees.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.insees.R
import com.example.insees.databinding.FragmentIntroBinding
import com.example.insees.util.FirebaseManager

class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private lateinit var navController:NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        val auth = FirebaseManager.getFirebaseAuth()

        binding.alreadyHaveAccount.setOnClickListener{
            if(auth.currentUser != null && auth.currentUser!!.isEmailVerified)
            {
                Toast.makeText(requireContext(), "User is already logged in", Toast.LENGTH_SHORT).show()
                redirect("MAIN")
                requireActivity().finish()
            }
            else
                redirect("LOGIN")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIntroBinding.inflate(inflater,container,false)

        binding.newUser.setOnClickListener {
            navController.navigate(R.id.action_introFragment_to_signUpFragment)
        }
        return binding.root
    }
    private fun redirect (name: String){
        when(name){
            "LOGIN" -> navController.navigate(R.id.action_introFragment_to_loginFragment)
            "MAIN" -> navController.navigate(R.id.action_introFragment_to_homeActivity)
            else -> throw Exception("no path exists")
        }
    }
}