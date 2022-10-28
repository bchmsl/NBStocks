package com.nbstocks.nbstocks.presentation.ui.company_listings.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nbstocks.nbstocks.databinding.LayoutSearchStockItemBinding
import com.nbstocks.nbstocks.presentation.ui.company_listings.model.CompanyListingUiModel

class CompanyListingsAdapter: ListAdapter<CompanyListingUiModel, CompanyListingsAdapter.StocksViewHolder>(callback) {

    var stockItemClicked: ((CompanyListingUiModel) -> Unit)? = null

    inner class StocksViewHolder(private val binding: LayoutSearchStockItemBinding): ViewHolder(binding.root){
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
        return StocksViewHolder(LayoutSearchStockItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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