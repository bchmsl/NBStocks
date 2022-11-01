package com.nbstocks.nbstocks.presentation.ui.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.toCurrencyString
import com.nbstocks.nbstocks.common.extensions.toPercentString
import com.nbstocks.nbstocks.common.extensions.toPercentStringTimes100
import com.nbstocks.nbstocks.databinding.LayoutWatchlistItemBinding
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel


class WatchlistStocksAdapter :
    ListAdapter<WatchlistStockInfoUiModel.DataItem, WatchlistStocksAdapter.WatchlistViewHolder>(
        callback
    ) {

    var stockItemClicked: ((WatchlistStockInfoUiModel.DataItem) -> Unit)? = null

    inner class WatchlistViewHolder(private val binding: LayoutWatchlistItemBinding) :
        ViewHolder(binding.root) {
        fun onBind() {
            val currentItem = getItem(adapterPosition)
            binding.apply {
                tvItemSymbol.text = currentItem.symbol
                tvWatchlistPrice.text = currentItem.regularMarketPrice.toCurrencyString()
                tvNameShort.text = currentItem.shortName
                tvExchangeName.text = currentItem.fullExchangeName
                currentItem.regularMarketChangePercent?.let {
                    if (currentItem.regularMarketChangePercent < 0) {
                        tvWatchlistPercentage.setTextColor(Color.RED)
                        ivChart.setImageResource(R.drawable.ic_decrease)
                    } else {
                        tvWatchlistPercentage.setTextColor(Color.GREEN)
                        ivChart.setImageResource(R.drawable.ic_increase)
                    }
                }
                tvWatchlistPercentage.text =
                    currentItem.regularMarketChangePercent.toPercentString()
                root.setOnClickListener { stockItemClicked?.invoke(currentItem) }
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