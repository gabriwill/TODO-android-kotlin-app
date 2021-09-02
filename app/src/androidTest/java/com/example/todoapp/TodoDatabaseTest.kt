package com.example.todoapp

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.database.ToDoDatabaseDao
import com.example.todoapp.database.ToDoEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.util.*

@RunWith(JUnit4::class)
class TodoDatabaseTest {

    private lateinit var toDoDao: ToDoDatabaseDao
    private lateinit var db: ToDoDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ToDoDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        toDoDao = db.todoDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetBetweenDates() {
        val todo = ToDoEntity()
        toDoDao.insert(todo)

        val calendar = Calendar.getInstance()
        calendar.set(2021,8,2,0,0,0)
        val startDate = calendar.timeInMillis
        calendar.set(2021,8,2,23,59,59)
        val endDate = calendar.timeInMillis
        val todos = toDoDao.getBetweenDates(startDate,endDate)
        Log.i("teste","$todo - ${todos}")
        Log.i("teste","$startDate - ${endDate}")
        Log.i("teste",todos?.get(0)?.title?:"nada")
        assertEquals(todos?.get(0)?.title, "title")
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAll() {
        val todo = ToDoEntity()
        toDoDao.insert(todo)

        val todos = toDoDao.getAll()
        Log.i("teste","$todo - ${todos}")
        assertEquals(todos?.get(0)?.title, "title")
    }
}