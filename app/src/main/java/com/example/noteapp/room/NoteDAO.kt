package com.example.noteapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
     @Insert
     //suspend keyword is similar to Javascript's Async Await It prevents the function from using main thread
    suspend fun insert(note: Note)
    @Update
    suspend fun update(note: Note)
    @Delete
    suspend fun delete(note: Note)
    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()
    @Query( "SELECT * FROM  note_table  ORDER BY id  ASC")
    //Flow retrieves data asynchronously
    fun getAllNotes():Flow<List<Note>>
}