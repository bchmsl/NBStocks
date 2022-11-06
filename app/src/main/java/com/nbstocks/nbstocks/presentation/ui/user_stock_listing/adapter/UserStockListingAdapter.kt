package com.nbstocks.nbstocks.presentation.ui.user_stock_listing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.safeSubString
import com.nbstocks.nbstocks.common.extensions.toCurrencyString
import com.nbstocks.nbstocks.common.extensions.toPercentString
import com.nbstocks.nbstocks.databinding.LayoutOwnStockItemFragmentBinding
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel


class UserStockListingAdapter : ListAdapter<WatchlistStockInfoUiModel.DataItem, UserStockListingAdapter.UserStockViewHolder>(callback) {

    var stockItemClicked: ((WatchlistStockInfoUiModel.DataItem) -> Unit)? = null

    inner class UserStockViewHolder(private val binding: LayoutOwnStockItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val currentItem = getItem(adapterPosition)


            binding.tvItemSymbol.text = currentItem.symbol
            binding.tvCurrentPrice.text = currentItem.regularMarketPrice.toCurrencyString()
            binding.tvLowPrice.text = currentItem.regularMarketDayLow.toCurrencyString()
            binding.tvHighPrice.text = currentItem.regularMarketDayHigh.toCurrencyString()
            binding.tvWatchlistPercentage.text = currentItem.regularMarketChangePercent.toPercentString()
            binding.tvExchangeName.text = currentItem.fullExchangeName
            binding.tvNameShort.text = currentItem.shortName
            currentItem.regularMarketChangePercent?.let {
                if (currentItem.regularMarketChangePercent < 0) {
                    binding.tvWatchlistPercentage.setBackgroundResource(R.drawable.shape_rectangle_decrease)
                    binding.ivChart.setImageResource(R.drawable.ic_decrease)
                } else {
                    binding.tvWatchlistPercentage.setBackgroundResource(R.drawable.shape_rectangle_increase)
                    binding.ivChart.setImageResource(R.drawable.ic_increase)
                }
            }
            binding.tvOwnedAmount.text = currentItem.ownedAmount.toString().safeSubString(7)
            binding.tvTotalValue.text =
                currentItem.regularMarketPrice?.let { currentItem.ownedAmount?.times(it).toCurrencyString() }

            binding.root.setOnClickListener {
                stockItemClicked!!.invoke(currentItem)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserStockViewHolder {
        return UserStockViewHolder(
            LayoutOwnStockItemFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserStockViewHolder, position: Int) {
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