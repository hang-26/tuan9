package com.example.oneexceriseactivity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        if (it.resultCode == Activity.RESULT_OK) {
            noteList = notesDataHelper.getAll()
            notesAdapter.refreshData(noteList)
        }
    }

    private val startActivityForResult2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {

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
             notesAdapter.refreshData(noteList)
         }

         binding.etSearch.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

             }

             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

             }

             override fun afterTextChanged(s: Editable?) {
                 val text = s.toString()
                 notesAdapter.searchNotes(text)
             }

         })

         binding.ivOption.setOnClickListener {
             selectSort()
         }

         binding.ivBtDelete.setOnClickListener {
             notesAdapter.deleteSelectItems()
         }
     }

    fun setNoteItem() {
        if (isDelete == false) {
            binding.ivBtDelete.visibility = View.GONE
        } else {
            binding.ivBtDelete.visibility = View.VISIBLE
        }
        noteList = notesDataHelper.getAll()
        notesAdapter = ItemNotesAdapter(this, noteList,  object : NotesInterface {
            override fun onClick(position: Int) {
                super.onClick(position)
                setOnClickNote(position)
                notesAdapter.notifyDataSetChanged()
            }

            override fun onLongClick(position: Int) {
                super.onLongClick(position)
                binding.ivBtDelete.visibility = View.VISIBLE
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
    }

    fun selectSort() {
        val sharedPreferences = getSharedPreferences("MyShare", Context.MODE_PRIVATE)
        var indexSelect = sharedPreferences.getInt("indext", 0)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Sorted by")
            .setPositiveButton("Đồng ý") {
                dialog, which ->
                when (indexSelect) {
                    0 -> {
                        indexSelect = 0
                        Toast.makeText(this,"Sắp xếp theo thời gian gần nhất", Toast.LENGTH_SHORT).show()
                        notesAdapter.sortListNew()
                    }
                    1 -> {
                        indexSelect = 1
                        Toast.makeText(this,"Sắp xếp theo thời gian gần nhất", Toast.LENGTH_SHORT).show()
                        notesAdapter.sortListOld()
                    }
                    2 -> {
                        indexSelect = 2
                        Toast.makeText(this,"Sắp xếp theo A -Z", Toast.LENGTH_SHORT).show()
                        notesAdapter.sortListAZ()
                    }
                    3 -> {
                        indexSelect = 3
                        Toast.makeText(this,"Sắp xếp theo Z- A", Toast.LENGTH_SHORT).show()
                        notesAdapter.sortListZA()
                    }
                }
                dialog.dismiss()  // Đóng dialog sau khi xử lý
                sharedPreferences.edit().putInt("indext", indexSelect).apply()
            }
            .setNegativeButton("Hủy") { dialog, which ->
                dialog.dismiss()
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
            .setSingleChoiceItems(
                arrayOf(
                        "Thời gian tạo gần nhất",
                        "Thời gian tạo cũ nhất",
                        "Tên từ A - Z",
                        "Tên từ Z - A"), indexSelect
            ) { dialog, which ->
               indexSelect = which
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}