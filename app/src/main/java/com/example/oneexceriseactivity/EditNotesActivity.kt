package com.example.oneexceriseactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.oneexceriseactivity.databinding.ActivityEditNotesBinding

class EditNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditNotesBinding
    lateinit var notesDataHelper: NotesDataHelper
    var noteId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notesDataHelper = NotesDataHelper(this)
        setFirstInterface()
        setEvent()
    }


    fun setFirstInterface() {
        val intentEditNote = intent
        var title = intentEditNote.getStringExtra("title")
        var contentNote = intentEditNote.getStringExtra("content")
        binding.etTitle.setText(title)
        binding.etContent.setText(contentNote)
    }

    fun setEvent() {
        binding.ivSave.setOnClickListener {
            updateNotes()
        }
    }

    fun updateNotes() {
        val  intentEditNote = intent
        noteId = intentEditNote.getIntExtra("id", -1)
        if (noteId == -1 ) {
            finish()
            return
        }

            var newTitle = binding.etTitle.text.toString()
            var newContent = binding.etContent.text.toString()
            notesDataHelper.editNote(newTitle, newContent, noteId)
            notesDataHelper.getAll()
            setResult(RESULT_OK)
            finish()
    }
}