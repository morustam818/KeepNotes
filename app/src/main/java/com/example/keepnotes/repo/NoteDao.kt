package com.example.keepnotes.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.keepnotes.repo.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("SELECT * FROM Note")
    fun getAllNotes() : LiveData<List<Note>>

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)
}