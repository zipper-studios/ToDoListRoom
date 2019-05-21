package com.example.kotlinpractice.roomtodolist.ui.main_activity

import android.arch.lifecycle.ViewModel
import com.example.kotlinpractice.roomtodolist.model.ItemToDo
import com.example.kotlinpractice.roomtodolist.persistance.Repository

class MainViewModel(val repository: Repository) : ViewModel() {

    val mainAdapter = MainAdapter()
    val itemsList = repository.getItemsFromLocalDatabase()

    fun updateList(it: List<ItemToDo>) {
        mainAdapter.updateList(it)
    }

    fun onResume() {
        repository.getItemsFromFirebase()
    }
}