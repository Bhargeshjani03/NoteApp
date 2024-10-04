package com.example.noteapp.room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.noteapp.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//This code defines a Room database for managing Note objects
//using a singleton pattern to ensure there is only one instance of the database at runtime.
//The NoteDatabase class is annotated with @Database and provides an abstract method to access the DAO.
//The companion object ensures thread-safe access to the database instance, preventing potential crashes
//or data inconsistencies in multi-threaded environments.
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDAO():NoteDAO
    //Singleton
    companion object{
        @Volatile
        private var  INSTANCE:NoteDatabase?=null
        fun  getDatabase(context:Context,scope: CoroutineScope):NoteDatabase
        {
            return INSTANCE  ?:  synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,
                         "note_database").addCallback(NoteDatabaseCallback(scope)).build()
                INSTANCE=instance
                instance
            }
        }
    }
    private class NoteDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->

//                database.getNoteDAO().insert(Note("t","d"))
                scope.launch {
                    val noteDAO= database.getNoteDAO()
                    noteDAO.insert(Note("title1","description1"))
                    noteDAO.insert(Note("title2","description2"))
                    noteDAO.insert(Note("title3","description3"))
                }

            }
        }
    }
}