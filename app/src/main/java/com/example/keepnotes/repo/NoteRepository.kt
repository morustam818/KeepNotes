package com.example.keepnotes.repo

import androidx.lifecycle.LiveData
import com.example.keepnotes.repo.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes : LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    fun update(note: Note){
        noteDao.update(note)
    }

    fun delete(note: Note){
        noteDao.delete(note)
    }

}