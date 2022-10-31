package com.nbstocks.nbstocks.presentation.ui.watchlist_listing.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbstocks.nbstocks.databinding.LayoutWatchlistItemBinding


class WatchlistAdapter : ListAdapter<String, WatchlistAdapter.WatchlistViewHolder>(callback) {

    var stockItemClicked: ((String) -> Unit)? = null

    inner class WatchlistViewHolder(private val binding: LayoutWatchlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val currentItem = getItem(adapterPosition)
            binding.tvItemSymbol.text = currentItem
//            binding.tvWatchlistPrice.text = currentItem.currentPrice?.fmt
//            binding.tvWatchlistPercentage.text = currentItem.revenueGrowth?.fmt
            binding.root.setOnClickListener {
                stockItemClicked!!.invoke(currentItem)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        return WatchlistViewHolder(
            LayoutWatchlistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        holder.onBind()
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}