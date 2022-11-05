package com.nbstocks.nbstocks.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.safeSubString
import com.nbstocks.nbstocks.common.extensions.toCurrencyDouble
import com.nbstocks.nbstocks.common.extensions.toCurrencyString
import com.nbstocks.nbstocks.databinding.LayoutTradeHistoryItemBinding
import com.nbstocks.nbstocks.presentation.ui.home.model.TradeHistoryUiModel


class TradeHistoryAdapter :
    ListAdapter<TradeHistoryUiModel, TradeHistoryAdapter.TradeHistoryViewHolder>(callback) {


    inner class TradeHistoryViewHolder(private val binding: LayoutTradeHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind() {
            val currentItem = getItem(adapterPosition)
            binding.apply {
                tvStockSymbol.text = currentItem.symbol
                tvStockTradeDate.text = currentItem.tradeDate.safeSubString(12)
                if (currentItem.isBuy.toString() == "true"){
                    binding.ivTradeType.setImageResource(R.drawable.buy_icon)
                    tvMoney.text = "-${currentItem.money.toCurrencyDouble().toCurrencyString()}"
                    tvMoney.setTextColor(Color.parseColor("#E82F46"))
                }else{
                    binding.ivTradeType.setImageResource(R.drawable.sell_icon)
                    tvMoney.text = currentItem.money.toCurrencyDouble().toCurrencyString()
                    tvMoney.setTextColor(Color.parseColor("#63C10A"))
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeHistoryViewHolder {
        return TradeHistoryViewHolder(
            LayoutTradeHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: TradeHistoryViewHolder, position: Int) {
        holder.onBind()
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<TradeHistoryUiModel>() {
            override fun areItemsTheSame(
                oldItem: TradeHistoryUiModel,
                newItem: TradeHistoryUiModel
            ): Boolean {
                return oldItem.symbol == newItem.symbol
            }

            override fun areContentsTheSame(
                oldItem: TradeHistoryUiModel,
                newItem: TradeHistoryUiModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}