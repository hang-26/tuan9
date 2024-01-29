package com.example.oneexceriseactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oneexceriseactivity.databinding.ActivityMainBinding

class MainActivity:  AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var noteList: MutableList<NoteData>
    lateinit var notesDataHelper: NotesDataHelper
    lateinit var notesAdapter: ItemNotesAdapter
    private val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
//            val e: String? = it.data?.getStringExtra("result")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notesDataHelper = NotesDataHelper(this)
        noteList = notesDataHelper.getAll()
        setNoteItem()
        setEvent()
    }
     fun setEvent() {
         binding.ivButtonAdd.setOnClickListener {
             val intent: Intent = Intent(this, AddNotesActivity::class.java)
             startActivityForResult.launch(intent)
         }
     }

    fun setNoteItem() {
        notesAdapter = ItemNotesAdapter(noteList)
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        binding.rvNoteApp.adapter = notesAdapter
        binding.rvNoteApp.layoutManager = layoutManager
    }
}