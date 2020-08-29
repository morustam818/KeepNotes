package com.example.keepnotes

import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.keepnotes.repo.model.Note
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_edit_note.*
import kotlinx.android.synthetic.main.activity_edit_note.view.*
import kotlinx.android.synthetic.main.activity_main.*

class EditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")
        et_edit_title.setText(title)
        et_edit_description.setText(desc)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.update_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.updateMenu){
            /*
            title set as a primary key so we can't change the title
             */
            val title = et_edit_title.text.toString()
            val desc = et_edit_description.text.toString()
           MainActivity.viewModel.update(Note(title,desc))
        }
        finish()
        return super.onOptionsItemSelected(item)
    }
}