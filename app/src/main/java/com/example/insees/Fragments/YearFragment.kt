package com.example.insees.Fragments

<<<<<<< HEAD
import com.example.insees.databinding.FragmentYearListBinding

import android.os.Bundle
=======
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
>>>>>>> origin/master
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
<<<<<<< HEAD
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.insees.R
import com.example.insees.Utils.FirebaseManager
import com.example.insees.Adapters.YearAdapter
import com.google.firebase.storage.StorageReference

class YearFragment : Fragment(){

    private lateinit var binding:FragmentYearListBinding
    private lateinit var subjectListView: ListView
    private lateinit var selectedSemester: String
    private lateinit var storageRef: StorageReference
    private lateinit var selectedYear: String
    private lateinit var fileName: String
    private lateinit var downloadUrl: String
=======
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.insees.Adapters.YearAdapter
import com.example.insees.R
import com.example.insees.Utils.FirebaseManager
import com.example.insees.databinding.FragmentSubjectListBinding
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.callbackFlow
import java.io.File

class YearFragment : Fragment(){

    private lateinit var binding:FragmentSubjectListBinding
    private lateinit var subjectListView: ListView
    private lateinit var selectedSemester: String
    private lateinit var storageRef: StorageReference
    private lateinit var fileName: String
    private lateinit var downloadUrl: String
    private var years: MutableList<String> = mutableListOf()
>>>>>>> origin/master

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
<<<<<<< HEAD
    ): View {
        binding = FragmentYearListBinding.inflate(inflater,container,false)
        subjectListView = binding.subjectsList

        // Here you can populate the subjects based on the selected semester
        val year = when (selectedSemester) {
            "Semester 1" -> arrayOf("Subject A", "Subject B", "Subject C")
            "Semester 2" -> arrayOf("Subject D", "Subject E", "Subject F")
            "Semester 3" -> arrayOf("2018", "2019", "2022","2023")
            "Semester 4" -> arrayOf("2000", "Subject K", "Subject L")
            "Semester 5" -> arrayOf("Subject M", "Subject N", "Subject O")
            "Semester 6" -> arrayOf("Subject P", "Subject Q", "Subject R")
            "Semester 7" -> arrayOf("Subject S", "Subject T", "Subject U")
            "Semester 8" -> arrayOf("Subject V", "Subject W", "Subject X")
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
            findNavController().navigate(R.id.action_yearFragment_to_semesterFragment)
=======
    ): View? {
        binding = FragmentSubjectListBinding.inflate(inflater,container,false)
        subjectListView = binding.subjectsList

        // Here you can populate the subjects based on the selected semester
        getYear(selectedSemester) {years->

            val adapter = YearAdapter(requireContext(), years.toTypedArray())
            subjectListView.adapter = adapter

            subjectListView.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val selectedYear = years[position]
                    getDataFromFirebase(selectedYear)

                }
        }

        val backbtn = binding.btnSubjectBack
        backbtn.setOnClickListener {
            findNavController().navigate(R.id.action_subjectsFragment_to_semesterFragment)
>>>>>>> origin/master

        }
        return binding.root
    }

<<<<<<< HEAD

    private fun getDataFromFirebase(selectedSemester: String,selectedYear:String ) {

        binding.progressBar.visibility = View.VISIBLE

        storageRef = FirebaseManager.getFirebaseStorage().reference.child("PYQs").child(selectedSemester)
            .child(selectedYear)
=======
    private fun getYear(selectedSemester: String, callback: (List<String>) -> Unit){
        storageRef = FirebaseManager.getFirebaseStorage().reference.child("PYQs").child(selectedSemester)

        years.clear()

        storageRef.listAll().addOnSuccessListener { result->

            for(prefix in result.prefixes){
                years.add(prefix.name)
            }

            callback(years)

        }.addOnFailureListener {exception->
            Toast.makeText(context, "Something went wrong: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromFirebase(selectedYear:String ) {

        storageRef = storageRef.child(selectedYear)
>>>>>>> origin/master

        storageRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.firstOrNull()?.let { fileRef ->
                    fileName = fileRef.name
<<<<<<< HEAD
                    fileRef.downloadUrl.addOnCompleteListener{
                        if(it.isSuccessful){
                            downloadUrl = it.result.toString()
                            findNavController().navigate(R.id.action_yearFragment_to_pdfViewerFragment, Bundle().apply {
                                putString("file_name", fileName)
                                putString("download_url", downloadUrl)
                            })
                            binding.progressBar.visibility = View.GONE
=======

                    val downloadDir = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"Insees")

                    if(!downloadDir.exists()){
                        downloadDir.mkdirs()
                    }

                    val file = File(downloadDir,fileName)

                    if (file.exists()) {
                        openPdf(file)
                    }
                    else{
                        binding.progressBar.visibility = View.VISIBLE
                    fileRef.downloadUrl.addOnCompleteListener{
                        if(it.isSuccessful) {
                            downloadUrl = it.result.toString()
                                findNavController().navigate(
                                    R.id.action_subjectsFragment_to_pdfViewerFragment,
                                    Bundle().apply {
                                        putString("file_name", fileName)
                                        putString("download_url", downloadUrl)
                                    })
                                binding.progressBar.visibility = View.GONE
                            }
>>>>>>> origin/master
                        }
                    }
                }
            }.addOnFailureListener {exception->
                Toast.makeText(context, "Error getting file: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

<<<<<<< HEAD
=======
    private fun openPdf(file:File){
            val path = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(path,"application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(intent)
    }

>>>>>>> origin/master

}