package com.example.keepnotes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.EditNoteActivity
import com.example.keepnotes.NewNoteActivity
import com.example.keepnotes.R
import com.example.keepnotes.databinding.NoteListBinding
import com.example.keepnotes.repo.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes : List<Note> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        DataBindingUtil.inflate<NoteListBinding>(LayoutInflater.from(parent.context),
        R.layout.note_list,parent,false).apply {
            return NoteViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBing(notes[position])
    }

    override fun getItemCount() = notes.size

    fun addNote(list : List<Note>){
        this.notes = list
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int) : Note = notes[position]

    inner class NoteViewHolder(private val binding: NoteListBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBing(note: Note){
            binding.note = note

            binding.root.setOnClickListener{
                Intent(binding.root.context,EditNoteActivity::class.java).apply {
                    putExtra("title",note.title)
                    putExtra("desc",note.description)
                    binding.root.context.startActivity(this)
                }
            }
        }
    }
}
