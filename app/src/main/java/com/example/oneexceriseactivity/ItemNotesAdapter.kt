package com.example.oneexceriseactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.oneexceriseactivity.databinding.ItemNoteBinding

class ItemNotesAdapter(var listNotes: MutableList<NoteData>) : RecyclerView.Adapter<ItemNotesAdapter.ViewHolder>()
{
    lateinit var binding: ItemNoteBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
}

    override fun getItemCount(): Int {
        return listNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteList = listNotes[holder.adapterPosition]
        holder.binding.tvTitle.text = noteList.title
        holder.binding.tvContent.text = noteList.content
        holder.binding.tvNotesTime.text = noteList.editTime.toString()
    }

    class ViewHolder(val binding: ItemNoteBinding):
        RecyclerView.ViewHolder(binding.root) {
    }

}