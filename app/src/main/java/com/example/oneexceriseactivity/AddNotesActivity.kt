package com.example.oneexceriseactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.oneexceriseactivity.databinding.ActivityAddNotesBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.util.Calendar

class AddNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNotesBinding
    lateinit var db: NotesDataHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = NotesDataHelper(this)
        setEvent()
    }

    fun setEvent() {
        binding.ivBtSave.setOnClickListener {
            var title = binding.etTitle.text.toString()
            var contentNote = binding.etContent.text.toString()
            // Lấy timestamp cho thời gian hiện tại
            val timeNotes = System.currentTimeMillis()
            val note = NoteData(0, title, contentNote, timeNotes)
            db.insertNotes(note)
            finish()
            Toast.makeText(this, " Ghi chú đã được thêm thành công ", Toast.LENGTH_SHORT).show()
        }
    }
}