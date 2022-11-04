package com.nbstocks.nbstocks.presentation.ui.user_stock_listing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.asynchronously
import com.nbstocks.nbstocks.common.extensions.collectViewState
import com.nbstocks.nbstocks.databinding.FragmentOwnStockItemsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.user_stock_listing.adapter.UserStockListingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserStockListingFragment :
    BaseFragment<FragmentOwnStockItemsBinding>(FragmentOwnStockItemsBinding::inflate) {

    private val viewModel: UserStockListingViewModel by viewModels()
//    private val userStockAdapter by lazy { UserStockListingAdapter() }

    override fun start() {
        observer()
    }

    private fun observer() {
        asynchronously {
            viewModel.getUsersStocks()
            viewModel.usersStockState.collectViewState(binding) { stockList ->

            }
        }
    }

    private fun setUpRecycler() {
        binding.rvWatchlistStocks.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvWatchlistStocks.adapter = userStockAdapter
    }

    private fun listeners() {

    }


}