package com.nbstocks.nbstocks.presentation.stocks.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nbstocks.nbstocks.databinding.SearchStockItemBinding
import com.nbstocks.nbstocks.presentation.model.CompanyListingUiModel

class StocksAdapter: ListAdapter<CompanyListingUiModel, StocksAdapter.StocksViewHolder>(callback) {

    var stockItemClicked: ((CompanyListingUiModel) -> Unit)? = null

    inner class StocksViewHolder(private val binding: SearchStockItemBinding): ViewHolder(binding.root){
        fun bind(){
            val currentItem = getItem(adapterPosition)
            binding.apply {
                tvItemCompanyName.text = currentItem.name
                tvType.text = currentItem.type?.name
                tvType.backgroundTintList = ColorStateList.valueOf(currentItem.type?.color ?: 0)
                tvItemSymbol.text = currentItem.symbol
                root.setOnClickListener{
                    stockItemClicked?.invoke(currentItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        return StocksViewHolder(SearchStockItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        holder.bind()
    }
    companion object{
        val callback = object: DiffUtil.ItemCallback<CompanyListingUiModel>(){
            override fun areItemsTheSame(
                oldItem: CompanyListingUiModel,
                newItem: CompanyListingUiModel
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: CompanyListingUiModel,
                newItem: CompanyListingUiModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}