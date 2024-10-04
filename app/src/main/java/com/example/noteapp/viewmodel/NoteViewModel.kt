package com.example.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.model.Note
import com.example.noteapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NoteViewModel(private val repository: Repository):ViewModel() {
    val myAllNotes:LiveData<List<Note>> = repository.myAllNotes.asLiveData()
    fun insert(note: Note)=viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }
    fun update(note: Note)=viewModelScope.launch(Dispatchers.IO){
        repository.update(note)
    }
    fun delete(note: Note)=viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }
    fun deleteAllNotes()=viewModelScope.launch(Dispatchers.IO){
        repository.deleteAllNotes()
    }
}
@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(private var repository: Repository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java))
        {
            return NoteViewModel(repository) as T
        }
        else
        {
            throw IllegalArgumentException("unknown view model")
        }
    }
}