package com.nbstocks.nbstocks.presentation.ui.watchlist_listing.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbstocks.nbstocks.databinding.LayoutWatchlistItemBinding
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel


class WatchlistAdapter : ListAdapter<WatchlistStockInfoUiModel.DataItem, WatchlistAdapter.WatchlistViewHolder>(callback) {

    var stockItemClicked: ((WatchlistStockInfoUiModel.DataItem) -> Unit)? = null

    inner class WatchlistViewHolder(private val binding: LayoutWatchlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val currentItem = getItem(adapterPosition)
            binding.tvItemSymbol.text = currentItem.symbol
            binding.tvWatchlistPrice.text = currentItem.regularMarketPrice.toString()
            binding.tvWatchlistPercentage.text = currentItem.regularMarketChangePercent.toString()
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
        val callback = object : DiffUtil.ItemCallback<WatchlistStockInfoUiModel.DataItem>() {
            override fun areItemsTheSame(
                oldItem: WatchlistStockInfoUiModel.DataItem,
                newItem: WatchlistStockInfoUiModel.DataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: WatchlistStockInfoUiModel.DataItem,
                newItem: WatchlistStockInfoUiModel.DataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}