package com.example.oneexceriseactivity

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.oneexceriseactivity.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.coroutineContext

class ItemNotesAdapter(
    var listNotes: MutableList<NoteData>,
    context: Context,
    val notesInterface: NotesInterface) :
    RecyclerView.Adapter<ItemNotesAdapter.ViewHolder>()
{
    lateinit var binding: ItemNoteBinding
    var isEnable = false
    var isSelected = false
    private val noteData: NotesDataHelper = NotesDataHelper(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

}

    override fun getItemCount(): Int {
        return listNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteList = listNotes[holder.adapterPosition]
        val binding = holder.binding
        binding.tvTitle.text = noteList.title
        binding.tvContent.text = noteList.content
        val noteTime = noteList.editTime
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(noteTime)
        val noteDate = dateFormat.format(date)
        binding.tvNotesTime.text = noteDate.toString()

        holder.itemView.setOnClickListener {
            notesInterface.onClick(holder.adapterPosition)
            notifyItemChanged(holder.adapterPosition)
            notifyDataSetChanged()
        }

        holder.itemView.setOnLongClickListener {
            notesInterface.onLongClick(holder.adapterPosition)

            true
        }

        binding.ivCheck.setOnClickListener {
            noteData.deleteNote(noteList.id)
            refreshData(noteData.getAll())
        }
    }

    class ViewHolder(val binding: ItemNoteBinding):
        RecyclerView.ViewHolder(binding.root) {
    }



    fun refreshData(newNotes: MutableList<NoteData>) {
        listNotes = newNotes
        notifyDataSetChanged()
    }
}