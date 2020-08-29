package com.example.keepnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.RoomDatabase
import com.example.keepnotes.adapter.NoteAdapter
import com.example.keepnotes.repo.NoteViewModel
import com.example.keepnotes.repo.model.Note
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var viewModel: NoteViewModel
    }
    private lateinit var title: String
    private lateinit var desc: String

    private val ACIVITY_RESULT_CODE = 1
    private val ACIVITY_Edit_RESULT_CODE = 2
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_main.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val adapter = NoteAdapter()
        rv_main.adapter = adapter

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {
            it?.let {
                adapter.addNote(it)
            }
        })

        val swipeController = SwipeController{
            viewModel.delete(adapter.getNoteAt(it))
        }
        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(rv_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addNote)
            startActivityForResult(Intent(this, NewNoteActivity::class.java), ACIVITY_RESULT_CODE)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACIVITY_RESULT_CODE && resultCode == RESULT_OK) {
            count++;
            data?.getStringExtra(NewNoteActivity.TITLE)?.let {title ->
                this.title = title+"$count"
            }
            data?.getStringExtra(NewNoteActivity.DESCRIPTION)?.let {desc ->
                this.desc = desc
            }
            viewModel.insert(Note(title, desc))
        }
        else {
            Snackbar.make(main_container, "Empty note discarded", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }
    }

}