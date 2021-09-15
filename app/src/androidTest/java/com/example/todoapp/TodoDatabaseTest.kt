package com.example.todoapp

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.database.ToDoDatabaseDao
import com.example.todoapp.database.ToDoEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
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
    fun insertAndGetBetweenDates() = runBlocking {
        val calendar = Calendar.getInstance()
        calendar.set(2021,8,2,12,0,0)

        val todo = ToDoEntity(date=calendar.timeInMillis)
        toDoDao.insert(todo)

        calendar.set(2021,8,2,0,0,0)
        val startDate = calendar.timeInMillis
        calendar.set(2021,8,2,23,59,59)
        val endDate = calendar.timeInMillis
        val todos = toDoDao.getBetweenDates(startDate,endDate)
        Log.i("teste","$todo - ${todos}")
        Log.i("teste","$startDate - ${endDate}")
        Log.i("teste",todos?.get(0)?.title?:"nada")
        assertEquals("title",todos?.get(0)?.title )
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAll() = runBlocking {
        val todo = ToDoEntity()
        toDoDao.insert(todo)

        val todos = toDoDao.getAll()
        Log.i("teste","$todo - ${todos}")
        assertEquals("title",todos?.get(0)?.title)
    }

    @Test
    @Throws(Exception::class)
    fun insertUpdateAndGetToDo() = runBlocking {
        val originTodo = ToDoEntity()
        toDoDao.insert(originTodo)

        val updateTodo = toDoDao.getAll()?.get(0)?.copy(title = "updated To-Do")
            ?:throw Throwable("Test failed")

        toDoDao.update(updateTodo)

        val todos = toDoDao.getAll()
        Log.i("teste","$originTodo - ${todos}")
        assertTrue(todos?.any {
            it.title == "updated To-Do"
        }?:false)
    }

    @Test
    @Throws(Exception::class)
    fun insertDeleteAndGetToDo() = runBlocking {
        val todo = ToDoEntity(title = "deleted To-Do")
        toDoDao.insert(todo)

        val deleteTodo = toDoDao.getAll()?.filter { it.title == "deleted To-Do"}?.get(0)
            ?:throw Throwable("Test failed")

        toDoDao.delete(deleteTodo)

        val todos = toDoDao.getAll()
        Log.i("teste","$todo - ${todos}")
        assertFalse(todos?.any {
            it.title == "deleted To-Do"
        }?:true)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetLatest() = runBlocking {
        val todo1 = ToDoEntity()
        toDoDao.insert(todo1)
        val todo2 = ToDoEntity(title="new title")
        toDoDao.insert(todo2)

        val todos = toDoDao.getLatest()
        Log.i("teste","$todo1 - ${todos}")
        assertEquals("new title",todos?.title)
    }
}