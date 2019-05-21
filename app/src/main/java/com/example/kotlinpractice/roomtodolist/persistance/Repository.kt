package com.example.kotlinpractice.roomtodolist.persistance

import android.arch.lifecycle.LiveData
import com.example.kotlinpractice.roomtodolist.model.ItemToDo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Repository(private val db: ToDoDatabase) {

    fun addToDoItem(itemToDo: ItemToDo) {
        val myRef = FirebaseDatabase.getInstance().reference.child(System.currentTimeMillis().toString())
        myRef.setValue(itemToDo)
    }

    fun getItemsFromFirebase() {
        val myRef = FirebaseDatabase.getInstance().reference

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                addFirebaseResponseToLocalDB(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                return
            }
        })
    }

    fun addFirebaseResponseToLocalDB(dataSnapshot: DataSnapshot) {
        val toDoItemList = ArrayList<ItemToDo>()
        val memberSnapshot = dataSnapshot.children
        for (child in memberSnapshot) {
            val itemToDo = child.getValue(ItemToDo::class.java)
            itemToDo?.let { toDoItemList.add(itemToDo) }
        }

        val runnable = Runnable {
            db.toDoDao().insertItems(toDoItemList)
        }
        Thread(runnable).start()
    }

    fun getItemsFromLocalDatabase(): LiveData<List<ItemToDo>> {
        return db.toDoDao().getAllToDoItems()
    }
}