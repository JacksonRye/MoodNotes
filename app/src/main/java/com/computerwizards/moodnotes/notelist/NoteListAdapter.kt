package com.computerwizards.moodnotes.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.computerwizards.moodnotes.database.Note
import com.computerwizards.moodnotes.databinding.ListItemBinding

class NoteListAdapter(val clickListener: NoteClickListener) :
    ListAdapter<Note, NoteListAdapter.NoteListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    class NoteListViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: NoteClickListener, note: Note) {
            binding.note = note
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NoteListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return NoteListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        return NoteListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }
}

class NoteClickListener(val clickListener: (note: Note) -> Unit) {
    fun onClick(note: Note) = clickListener(note)
}