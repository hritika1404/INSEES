package com.example.insees.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.insees.R
import com.example.insees.Utils.FirebaseManager
import com.example.insees.Utils.YearAdapter
import com.example.insees.databinding.FragmentSubjectListBinding
import com.google.firebase.storage.StorageReference

class YearFragment : Fragment(){

    private lateinit var binding:FragmentSubjectListBinding
    private lateinit var subjectListView: ListView
    private lateinit var selectedSemester: String
    private lateinit var storageRef: StorageReference
    private lateinit var selectedYear: String
    private lateinit var fileName: String
    private lateinit var downloadUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedSemester = it.getString("selected_semester", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubjectListBinding.inflate(inflater,container,false)
        subjectListView = binding.subjectsList

        // Here you can populate the subjects based on the selected semester
        val year = when (selectedSemester) {
            "Semester 1" -> arrayOf("Subject A", "Subject B", "Subject C")
            "Semester 2" -> arrayOf("Subject D", "Subject E", "Subject F")
            "Semester 3" -> arrayOf("2018", "2019", "2022","2023")
            "Semester 4" -> arrayOf("Subject J", "Subject K", "Subject L")
            "Semester 5" -> arrayOf("Subject M", "Subject N", "Subject L")
            "Semester 6" -> arrayOf("Subject J", "Subject K", "Subject L")
            "Semester 7" -> arrayOf("Subject J", "Subject K", "Subject L")
            "Semester 8" -> arrayOf("Subject J", "Subject K", "Subject L")
            else -> arrayOf()

        }


        val adapter = YearAdapter(requireContext(), year)
        subjectListView.adapter = adapter

        subjectListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedYear = year[position]
                getDataFromFirebase(selectedSemester,selectedYear)

            }

        val backbtn = binding.btnSubjectBack
        backbtn.setOnClickListener {
            findNavController().navigate(R.id.action_subjectsFragment_to_semesterFragment)

        }
        return binding.root
    }


    private fun getDataFromFirebase(selectedSemester: String,selectedYear:String ) {

        binding.progressBar.visibility = View.VISIBLE

        storageRef = FirebaseManager.getFirebaseStorage().reference.child("PYQs").child(selectedSemester)
            .child(selectedYear)

        storageRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.firstOrNull()?.let { fileRef ->
                    fileName = fileRef.name
                    fileRef.downloadUrl.addOnCompleteListener{
                        if(it.isSuccessful){
                            downloadUrl = it.result.toString()
                            findNavController().navigate(R.id.action_subjectsFragment_to_pdfViewerFragment, Bundle().apply {
                                putString("file_name", fileName)
                                putString("download_url", downloadUrl)
                            })
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }.addOnFailureListener {exception->
                Toast.makeText(context, "Error getting file: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


}