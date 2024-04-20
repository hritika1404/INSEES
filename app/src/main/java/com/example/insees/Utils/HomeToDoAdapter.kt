package com.example.insees.Utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.insees.R
import com.example.insees.databinding.ItemTaskBinding
import java.util.LinkedList

class HomeToDoAdapter(private val list: MutableList<ToDoData>) :
    RecyclerView.Adapter<HomeToDoAdapter.HomeToDoViewHolder>() {
    private val colorResources = arrayOf(R.color.lighttyellow, R.color.purpleblue, R.color.mediumGreen, R.color.darkRed)
    private val colors = LinkedList<Int>()

    inner class HomeToDoViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeToDoViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        for (color in colorResources)
        {
            colors.push(color)
        }
        return HomeToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun getItem(position: Int) : ToDoData{
        return list[position]
    }

    override fun onBindViewHolder(holder: HomeToDoViewHolder, position: Int) {
        val task = list[position]
        val color = colors.first()
        colors.pop()
        colors.push(color)
        with(holder){
            binding.task2.text = task.taskTitle
            binding.task2.setBackgroundColor(color)
        }
    }
}
