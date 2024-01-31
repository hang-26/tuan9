package com.example.oneexceriseactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oneexceriseactivity.databinding.ActivityMainBinding

class MainActivity:  AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var noteList: MutableList<NoteData>
    lateinit var notesDataHelper: NotesDataHelper
    lateinit var notesAdapter: ItemNotesAdapter
    var isDelete = false
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
        if (isDelete == false) {
            binding.ivBtDelete.visibility = View.GONE
        } else {
            binding.ivBtDelete.visibility = View.VISIBLE
        }
        noteList = notesDataHelper.getAll()
        notesAdapter = ItemNotesAdapter(noteList, this, object : NotesInterface {
            override fun onClick(position: Int) {
                super.onClick(position)
                setOnClickNote(position)
                noteList = notesDataHelper.getAll()
                notesAdapter.listNotes = noteList
                notesAdapter.notifyDataSetChanged()
            }

            override fun onLongClick(position: Int) {
                super.onLongClick(position)
            }

        })
        val layoutManager = LinearLayoutManager(this)
        binding.rvNoteApp.adapter = notesAdapter
        binding.rvNoteApp.layoutManager = layoutManager
    }

    fun setOnClickNote(position: Int) {
        val intent = Intent(this, EditNotesActivity::class.java)
        intent.putExtra("id",  noteList[position].id)
        intent.putExtra("title",  noteList[position].title)
        intent.putExtra("content", noteList[position].content)
        startActivityForResult.launch(intent)
        setNoteItem()
    }




}