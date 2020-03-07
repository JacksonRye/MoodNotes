package com.computerwizards.moodnotes.online

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.computerwizards.moodnotes.databinding.OnlineFragmentBinding
import com.computerwizards.moodnotes.online.model.ChildData

class RedditListAdapter(val clickListener: RedditClickListener) :
    ListAdapter<ChildData, RedditListAdapter.RedditListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ChildData>() {
        override fun areItemsTheSame(oldItem: ChildData, newItem: ChildData): Boolean {
//            TODO("Add id to data of ChildData")
            return oldItem.data.title == newItem.data.title
        }

        override fun areContentsTheSame(oldItem: ChildData, newItem: ChildData): Boolean {
            return oldItem == newItem
        }
    }

    class RedditListViewHolder(private var binding: OnlineFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: RedditClickListener, childData: ChildData) {
            binding.child = childData
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RedditListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OnlineFragmentBinding.inflate(layoutInflater, parent, false)
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


class RedditClickListener(val clickListener: (childData: ChildData) -> Unit) {
    fun onClick(childData: ChildData) = clickListener(childData)
}