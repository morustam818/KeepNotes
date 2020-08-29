package com.example.keepnotes.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.keepnotes.repo.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class],version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao

    private class NoteDatabaseCallbacks(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {
                scope.launch {
                    val noteDao = it.noteDao()

                    val note = Note("Note 1","Hello")

                    noteDao.insert(note)
                }
            }
        }
    }

    companion object{
        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getDatabase(context: Context,coroutineScope: CoroutineScope) : NoteDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).addCallback(NoteDatabaseCallbacks(coroutineScope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

}