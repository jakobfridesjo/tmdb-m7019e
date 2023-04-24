package com.ltu.m7019e.v23.themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.databinding.ReviewListItemBinding

class ReviewListAdapter(private val reviewClickListener: ReviewListClickListener) :  ListAdapter<Review, ReviewListAdapter.ViewHolder>(ReviewListDiffCallback()){
    class ViewHolder(private var binding: ReviewListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review, reviewClickListener: ReviewListClickListener) {
            binding.review = review
            binding.clickListener = reviewClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ReviewListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), reviewClickListener)
    }
}

class ReviewListDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }

}

class ReviewListClickListener(val clickListener: (review: Review) -> Unit) {
    fun onClick(review: Review) = clickListener(review)
}