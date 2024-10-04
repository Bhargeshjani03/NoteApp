package com.example.noteapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.noteapp.R

class NoteAddActivity : AppCompatActivity() {
    private lateinit var editTextTitle:EditText
    private lateinit var editTextDescription:EditText
    private lateinit var buttonCancel: Button
    private lateinit var buttonSave:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note_add)
        supportActionBar?.title = "Add Note"
        editTextTitle = findViewById(R.id.editTextNoteTitle)
        editTextDescription=findViewById(R.id.editTextNoteDesc)
        buttonCancel = findViewById(R.id.buttonCancel)
        buttonSave=findViewById(R.id.buttonSave)
        buttonCancel.setOnClickListener {
            Toast.makeText(applicationContext,"Nothing Saved",Toast.LENGTH_LONG).show()
            finish()
        }
        buttonSave.setOnClickListener {
        saveNote()

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun saveNote()
    {
         val noteTitle : String=editTextTitle.text.toString()
        val noteDescription:String=editTextDescription.text.toString()
        val intent=Intent()
        intent.putExtra("title",noteTitle)
        intent.putExtra("description",noteDescription)
        setResult(RESULT_OK,intent)
        finish()
    }
}