package com.example.kotlinpractice.roomtodolist.ui.main_activity

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlinpractice.roomtodolist.R
import com.example.kotlinpractice.roomtodolist.databinding.ItemToDoBinding
import com.example.kotlinpractice.roomtodolist.model.ItemToDo

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var toDoList: List<ItemToDo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemToDoBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_to_do,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(toDoList[holder.adapterPosition], holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun updateList(toDoItemList: List<ItemToDo>) {
        toDoList = toDoItemList
        notifyDataSetChanged()
    }


    class ViewHolder(private val binding: ItemToDoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemToDo: ItemToDo, position: Int) {
            binding.model = itemToDo
            binding.position = position + 1
        }
    }
}