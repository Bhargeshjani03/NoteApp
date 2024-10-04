package com.example.noteapp.adapters
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.View.MainActivity
import com.example.noteapp.View.UpdateActivity
import com.example.noteapp.model.Note

class NoteAdapter(private val activity:MainActivity) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes : List<Note> = ArrayList()
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textviewTitle : TextView=itemView.findViewById(R.id.textViewTitle)
        val textviewDescription : TextView = itemView.findViewById(R.id.textViewDescription)
        val cardView : CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
       return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
         val currentNote:Note=notes[position]

        holder.textviewTitle.text = currentNote.title
        holder.textviewDescription.text=currentNote.description
        holder.cardView.setOnClickListener {
            val intent=Intent(activity,UpdateActivity::class.java)
            intent.putExtra("currentTitle",currentNote.title)
            intent.putExtra("currentDescription",currentNote.description)
            intent.putExtra("currentId",currentNote.id)
            //activity result launcher class
            activity.updateActivityResultLauncher.launch(intent)
        }
    }
    fun setNote(myNotes : List<Note>){
        this.notes=myNotes
        notifyDataSetChanged()
    }
    fun getNote(position: Int) : Note
    {
        return notes[position]
    }
}