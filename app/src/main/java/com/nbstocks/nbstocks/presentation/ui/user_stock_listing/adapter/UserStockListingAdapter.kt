package com.nbstocks.nbstocks.presentation.ui.user_stock_listing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.toCurrencyString
import com.nbstocks.nbstocks.common.extensions.toPercentString
import com.nbstocks.nbstocks.databinding.FragmentOwnStockItemsBinding
import com.nbstocks.nbstocks.databinding.LayoutOwnStockItemBinding
import com.nbstocks.nbstocks.databinding.LayoutOwnStockItemFragmentBinding
import com.nbstocks.nbstocks.databinding.LayoutWatchlistItemFragmentBinding
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel


class UserStockListingAdapter()