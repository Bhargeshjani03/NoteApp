package com.example.noteapp

import android.app.Application
import com.example.noteapp.repository.Repository
import com.example.noteapp.room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication:Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { NoteDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { Repository(database.getNoteDAO()) }
}