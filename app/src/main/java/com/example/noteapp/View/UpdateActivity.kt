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

class UpdateActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonCancel: Button
    private lateinit var buttonSave: Button
    private var currentId=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
        supportActionBar?.title = "Update Note"
        editTextTitle = findViewById(R.id.editTextNoteTitleUpdate)
        editTextDescription=findViewById(R.id.editTextNoteDescUpdate)
        buttonCancel = findViewById(R.id.buttonCancelUpdate)
        buttonSave=findViewById(R.id.buttonSaveUpdate)
        getAndSetData()
        buttonCancel.setOnClickListener {
            Toast.makeText(applicationContext,"Nothing updated", Toast.LENGTH_LONG).show()
            finish()
        }
        buttonSave.setOnClickListener {
            updateNote()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    private fun updateNote(){
        val updatedTitle=editTextTitle.text.toString()
        val updatedDescription=editTextDescription.text.toString()

        val intent=Intent()
        intent.putExtra("updatedTitle",updatedTitle)
        intent.putExtra("updatedDescription",updatedDescription)
        if(currentId!=-1)
        {
            intent.putExtra("noteId",currentId)
            setResult(RESULT_OK,intent)
            finish()
        }
    }
    private fun getAndSetData()
    {
        val currentTitle=intent.getStringExtra("currentTitle")
        val currentDescription=intent.getStringExtra("currentDescription")
         currentId=intent.getIntExtra("currentId",-1)
        //set the data
        editTextTitle.setText(currentTitle)
        editTextDescription.setText(currentDescription)

    }
}