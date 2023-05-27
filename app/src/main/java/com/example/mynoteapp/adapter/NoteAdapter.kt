package com.example.mynoteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ekasoftware.mynoteapp.R
import com.example.mynoteapp.model.Note
import com.example.mynoteapp.view.NotesFragmentDirections

class NoteAdapter
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(var view : View) : RecyclerView.ViewHolder(view)

    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.comment == newItem.comment &&
                        oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.note_row, parent, false)
        return NoteViewHolder(view)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemView.findViewById<TextView>(R.id.title).text =
            currentNote.title
        holder.itemView.findViewById<TextView>(R.id.comment).text =
            currentNote.comment


        holder.itemView.findViewById<CardView>(R.id.cardView).setOnClickListener {view->
            val action = NotesFragmentDirections.
            actionNotesFragmentToUpdateNoteFragment(currentNote)
            Navigation.findNavController(view).navigate(action)
        }
    }
}