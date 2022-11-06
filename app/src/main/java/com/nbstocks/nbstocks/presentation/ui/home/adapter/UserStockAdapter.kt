package com.nbstocks.nbstocks.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.safeSubString
import com.nbstocks.nbstocks.common.extensions.toCurrencyString
import com.nbstocks.nbstocks.common.extensions.toPercentString
import com.nbstocks.nbstocks.databinding.LayoutOwnStockItemBinding
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel

class UserStockAdapter :
    ListAdapter<WatchlistStockInfoUiModel.DataItem, UserStockAdapter.WatchlistViewHolder>(callback) {

    var stockItemClicked: ((WatchlistStockInfoUiModel.DataItem) -> Unit)? = null

    inner class WatchlistViewHolder(private val binding: LayoutOwnStockItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val currentItem = getItem(adapterPosition)
            binding.tvItemSymbol.text = currentItem.symbol
            binding.tvItemPrice.text = currentItem.regularMarketPrice.toCurrencyString()
            binding.tvPercentage.text = currentItem.regularMarketChangePercent.toPercentString()
            binding.tvOwnedAmount.text = currentItem.ownedAmount.toString().safeSubString(7)
            binding.tvTotalValue.text = (currentItem.ownedAmount?.let {
                currentItem.regularMarketPrice?.times(
                    it
                )
            }).toCurrencyString()
            currentItem.regularMarketChangePercent?.let {
                if (currentItem.regularMarketChangePercent < 0) {
                    binding.tvPercentage.setBackgroundResource(R.drawable.shape_rectangle_decrease)
                } else {
                    binding.tvPercentage.setBackgroundResource(R.drawable.shape_rectangle_increase)
                }
            }
            binding.root.setOnClickListener { stockItemClicked?.invoke(currentItem) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        return WatchlistViewHolder(
            LayoutOwnStockItemBinding.inflate(
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
                return oldItem.symbol == newItem.symbol
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