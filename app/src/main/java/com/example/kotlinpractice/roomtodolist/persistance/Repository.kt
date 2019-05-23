package com.example.kotlinpractice.roomtodolist.persistance

import android.arch.lifecycle.LiveData
import com.example.kotlinpractice.roomtodolist.model.ItemToDo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class Repository(private val db: ToDoDatabase) {

    private var comp: CompositeDisposable = CompositeDisposable()

    fun addToDoItem(itemToDo: ItemToDo) {
        val firebaseReference = FirebaseDatabase.getInstance().reference.child(System.currentTimeMillis().toString())
        firebaseReference.setValue(itemToDo)
    }

    fun getItemsFromFirebase() {
        val firebaseReference = FirebaseDatabase.getInstance().reference

        firebaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
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
        insertItemsInLocalDB(toDoItemList)
    }

    fun getItemsFromLocalDatabase(): LiveData<List<ItemToDo>> {
        return db.toDoDao().getAllToDoItems()
    }

    fun insertItemsInLocalDB(toDoItemList: ArrayList<ItemToDo>) {
        comp.add(Observable.fromCallable { db.toDoDao().insertItems(toDoItemList) }
            .subscribeOn(Schedulers.io())
            .subscribe())
    }
}