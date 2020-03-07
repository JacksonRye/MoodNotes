package com.computerwizards.moodnotes.online

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.computerwizards.moodnotes.database.Reddit
import com.computerwizards.moodnotes.databinding.RedditItemBinding

class RedditListAdapter(val clickListener: RedditClickListener) :
    ListAdapter<Reddit, RedditListAdapter.RedditListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Reddit>() {
        override fun areItemsTheSame(oldItem: Reddit, newItem: Reddit): Boolean {
//            TODO("Add id to data of Reddit")
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Reddit, newItem: Reddit): Boolean {
            return oldItem == newItem
        }
    }

    class RedditListViewHolder(private var binding: RedditItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: RedditClickListener, reddit: Reddit) {
            binding.reddit = reddit
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RedditListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RedditItemBinding.inflate(layoutInflater, parent, false)
                return RedditListViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditListViewHolder {
        return RedditListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RedditListViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }
}


class RedditClickListener(val clickListener: (reddit: Reddit) -> Unit) {
    fun onClick(reddit: Reddit) = clickListener(reddit)
}