package com.example.noteapp.View

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.NoteApplication
import com.example.noteapp.R
import com.example.noteapp.adapters.NoteAdapter
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewModel
import com.example.noteapp.viewmodel.NoteViewModelFactory
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
     lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>
    val noteAdapter = NoteAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = noteAdapter
        registerActivityLauncher()
        // Initialize ViewModel using the factory
        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        // Observe the LiveData from ViewModel
        noteViewModel.myAllNotes.observe(this, Observer { notes ->
            // Update UI
            noteAdapter.setNote(notes)
        })
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)
        // Apply window insets
    }

    private fun registerActivityLauncher() {
        addActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { resultAddNote ->
                    val resultCode = resultAddNote.resultCode
                    val data = resultAddNote.data

                    if (resultCode == RESULT_OK && data != null) {
                        val noteTitle: String = data.getStringExtra("title").toString()
                        val noteDescription = data.getStringExtra("description").toString()
                        val note = Note(noteTitle, noteDescription)
                        noteViewModel.insert(note)
                    }
                })
        updateActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { resultUpdateNote ->
                    val resultCode = resultUpdateNote.resultCode
                    val data = resultUpdateNote.data
                    if (resultCode == RESULT_OK && data != null) {
                        val updatedTitle : String= data.getStringExtra("updatedTitle").toString()
                        val updatedDescription : String=data.getStringExtra("updatedDescription").toString()
                        val noteId=data.getIntExtra("noteId",-1)

                        val newNote=Note(updatedTitle,updatedDescription)
                        newNote.id=noteId
                        noteViewModel.update(newNote)
                    }
                })
    }
        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.new_menu, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.item_add_note -> {
                    val intent = Intent(this, NoteAddActivity::class.java)
                    addActivityResultLauncher.launch(intent)
                }

                R.id.item_delete_all_notes -> showDialogMessage()
            }
            return true
        }
            private fun showDialogMessage()
            {
                val dialogMessage = AlertDialog.Builder(this)
                dialogMessage.setTitle("Delete All Notes")
                dialogMessage.setMessage("If click yes all notes will be deleted")
                dialogMessage.setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                dialogMessage.setPositiveButton("yes",DialogInterface.OnClickListener { dialog, which ->
                    noteViewModel.deleteAllNotes()
                })
                dialogMessage.create().show()


            }
    }

