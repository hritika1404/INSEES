package com.example.insees.Fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.insees.Utils.DialogAddBtnClickListener
import com.example.insees.R
import com.example.insees.Utils.ToDoData
import com.example.insees.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(), DialogAddBtnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var databaseRef : DatabaseReference
    private lateinit var popUpFragment : PopUpFragment
    private var tasks : MutableList<ToDoData> = mutableListOf()
    private lateinit var currentUser : FirebaseUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        init()
        fetchData()
        setUpViews()
        registerEvents()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpViews()

        binding.cardViewStudyMaterials.setOnClickListener{
            navController.navigate(R.id.action_homeFragment_to_semesterFragment)
        }

        binding.btnProfile.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_profileFragment)
        }

        binding.cardViewInsees.setOnClickListener{
            navController.navigate(R.id.action_homeFragment_to_inseesAboutInseesFragment)
        }

        binding.cardViewMembers.setOnClickListener{
            navController.navigate(R.id.action_homeFragment_to_aboutMembersFragment)
        }

        binding.btnViewAll.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_todoFragment)
        }

        binding.btnAddTask.setOnClickListener{
            navController.navigate(R.id.action_homeFragment_to_popUpFragment)
        }

        return binding.root
    }

    private fun setUpViews() {
        databaseRef = FirebaseDatabase.getInstance().getReference("users")
        currentUser = FirebaseAuth.getInstance().currentUser!!
        databaseRef.child(currentUser.uid).get().addOnSuccessListener {userData->
            if(userData.exists())
            {
                val name = userData.child("name").getValue(String::class.java)
                val profilePhoto = userData.child("profile_photo").getValue(String::class.java)?.let { Uri.parse(it) }
                var userName = name
                userName = "Hello $userName"
                binding.tvHello.text = userName
                binding.btnProfile.setImageURI(profilePhoto)
            }
            else
            {
                Toast.makeText(context, "User Doesn't Exist!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerEvents() {
        binding.btnAddTask.setOnClickListener {
            popUpFragment = PopUpFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(childFragmentManager,
                "PopUpFragment")
        }
    }
    private fun init() {
        currentUser = FirebaseAuth.getInstance().currentUser!!
        val user = currentUser.uid
        databaseRef = user.let {
            FirebaseDatabase.getInstance().reference
                .child("users").child(it)
        }
    }
    override fun onSaveTask(
        todoTitle: String,
        todoTitleEt: EditText,
        todoDesc: String,
        todoDescEt: EditText,
        todoTime: String,
        todoTimeEt: TextView,
        todoDate: String,
        todoDateEt: TextView
    ) {
        val task = hashMapOf(
            "title" to todoTitle,
            "description" to todoDesc,
            "time" to todoTime,
            "date" to todoDate
        )

        databaseRef.child("Tasks").push().setValue(task).addOnCompleteListener { tasks ->
            if (tasks.isSuccessful) {
                Toast.makeText(context, "Todo Saved Successfully", Toast.LENGTH_SHORT).show()
                todoTitleEt.text = null
                todoDescEt.text = null
                todoDateEt.text = null
                todoTimeEt.text = null
            } else {
                Toast.makeText(context, tasks.exception.toString(), Toast.LENGTH_SHORT).show()
            }
            popUpFragment.dismiss()
        }
    }
    private  fun fetchData() {
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")
        val query = databaseRef.child(currentUser.uid)
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tasks.clear()
                for (taskSnapshot in snapshot.children) {
                    val taskTitle=taskSnapshot.child("title").getValue(String::class.java) ?:""
                    val taskDesc=taskSnapshot.child("description").getValue(String::class.java)?:""
                    val taskTime=taskSnapshot.child("time").getValue(String::class.java)?:""
                    val taskDate=taskSnapshot.child("date").getValue(String::class.java)?:""

                    val todoTask=ToDoData(taskTitle,taskDesc,taskTime,taskDate)
                    tasks.add(todoTask)
                }
                tasks.sortWith(compareBy({
                    it.taskDate }, {it.taskTime}))

                val size = tasks.size
                if(size >= 4)
                {
                    binding.task1.visibility = View.VISIBLE
                    binding.task2.visibility = View.VISIBLE
                    binding.task3.visibility = View.VISIBLE
                    binding.task4.visibility = View.VISIBLE

                    binding.task1.text = tasks[0].taskTitle
                    binding.task2.text = tasks[1].taskTitle
                    binding.task3.text = tasks[2].taskTitle
                    binding.task4.text = tasks[3].taskTitle
                }
                else if (size == 3)
                {
                    binding.task1.visibility = View.VISIBLE
                    binding.task2.visibility = View.VISIBLE
                    binding.task3.visibility = View.VISIBLE
                    binding.task4.visibility = View.GONE

                    binding.task1.text = tasks[0].taskTitle
                    binding.task2.text = tasks[1].taskTitle
                    binding.task3.text = tasks[2].taskTitle
                }
                else if (size == 2)
                {
                    binding.task1.visibility = View.VISIBLE
                    binding.task2.visibility = View.VISIBLE
                    binding.task3.visibility = View.GONE
                    binding.task4.visibility = View.GONE

                    binding.task1.text = tasks[0].taskTitle
                    binding.task2.text = tasks[1].taskTitle
                }
                else if (size == 1)
                {
                    binding.task1.visibility = View.VISIBLE
                    binding.task2.visibility = View.GONE
                    binding.task3.visibility = View.GONE
                    binding.task4.visibility = View.GONE

                    binding.task1.text = tasks[0].taskTitle
                }
                else
                {
                    binding.task1.visibility = View.GONE
                    binding.task2.visibility = View.GONE
                    binding.task3.visibility = View.GONE
                    binding.task4.visibility = View.GONE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error in Fetching data", Toast.LENGTH_SHORT).show()
            }

        })

    }
}