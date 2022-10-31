package com.nbstocks.nbstocks.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.nbstocks.nbstocks.databinding.LayoutOwnStockItemBinding
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel

class UserStockAdapter :
    ListAdapter<UsersStockUiModel, UserStockAdapter.WatchlistViewHolder>(callback) {

    var stockItemClicked: ((UsersStockUiModel) -> Unit)? = null

    inner class WatchlistViewHolder(private val binding: LayoutOwnStockItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val currentItem = getItem(adapterPosition)
            binding.tvItemSymbol.text = currentItem.symbol
            binding.tvItemPrice.text = currentItem.price
            binding.tvItemPercentage.text = currentItem.amountInStocks
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
        val callback = object : DiffUtil.ItemCallback<UsersStockUiModel>() {
            override fun areItemsTheSame(
                oldItem: UsersStockUiModel,
                newItem: UsersStockUiModel
            ): Boolean {
                return oldItem.symbol == newItem.symbol
            }

            override fun areContentsTheSame(
                oldItem: UsersStockUiModel,
                newItem: UsersStockUiModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}