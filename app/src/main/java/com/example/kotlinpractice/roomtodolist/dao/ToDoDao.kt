package com.example.kotlinpractice.roomtodolist.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.kotlinpractice.roomtodolist.model.ItemToDo

@Dao
interface ToDoDao {

    @Query("SELECT * from itemToDo")
    fun getAllToDoItems(): LiveData<List<ItemToDo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(list: List<ItemToDo>)
}