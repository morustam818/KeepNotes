package com.example.keepnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saveNote) {
            val resultIntent = Intent()
            if (et_note.text.toString().isEmpty()) {
                setResult(RESULT_CANCELED, resultIntent)
            } else {
                resultIntent.putExtra(DESCRIPTION, et_note.text.toString())
                /*
                as we know title set as a primary key so title must be different every time!!!!
                otherwise room can't store data.
                 */
                resultIntent.putExtra(TITLE, et_title.text.toString())
                setResult(RESULT_OK, resultIntent)
            }

            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val DESCRIPTION = "com.example.keepnotes.REPLY"
        const val TITLE = "com.example.keepnotes.Title"
    }
}