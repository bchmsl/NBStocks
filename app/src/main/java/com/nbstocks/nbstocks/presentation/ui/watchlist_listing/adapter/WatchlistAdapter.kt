package com.nbstocks.nbstocks.presentation.ui.watchlist_listing.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbstocks.nbstocks.databinding.WatchlistItemBinding
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel


class WatchlistAdapter : ListAdapter<CurrentStockUiModel, WatchlistAdapter.WatchlistViewHolder>(callback) {

    var stockItemClicked: ((CurrentStockUiModel) -> Unit)? = null

    inner class WatchlistViewHolder(private val binding: WatchlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val currentItem = getItem(adapterPosition)
            binding.tvItemSymbol.text = currentItem.symbol
            binding.tvWatchlistPrice.text = currentItem.price
            binding.tvWatchlistPercentage.text = currentItem.changePercent
            binding.root.setOnClickListener {
                stockItemClicked!!.invoke(currentItem)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        return WatchlistViewHolder(
            WatchlistItemBinding.inflate(
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
        val callback = object : DiffUtil.ItemCallback<CurrentStockUiModel>() {
            override fun areItemsTheSame(
                oldItem: CurrentStockUiModel,
                newItem: CurrentStockUiModel
            ): Boolean {
                return oldItem.symbol == newItem.symbol
            }

            override fun areContentsTheSame(
                oldItem: CurrentStockUiModel,
                newItem: CurrentStockUiModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}