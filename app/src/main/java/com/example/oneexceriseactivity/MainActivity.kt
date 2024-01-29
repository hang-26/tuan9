package com.example.oneexceriseactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.oneexceriseactivity.databinding.ActivityMainBinding

class MainActivity:  AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
//            val e: String? = it.data?.getStringExtra("result")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }
     fun setEvent() {
         binding.ivButtonAdd.setOnClickListener {
             val intent: Intent = Intent(this, AddNotesActivity::class.java)
             startActivityForResult.launch(intent)
         }
     }
}