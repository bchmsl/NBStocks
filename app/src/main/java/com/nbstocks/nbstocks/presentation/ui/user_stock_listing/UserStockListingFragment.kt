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
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import com.nbstocks.nbstocks.presentation.ui.user_stock_listing.adapter.UserStockListingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserStockListingFragment :
    BaseFragment<FragmentOwnStockItemsBinding>(FragmentOwnStockItemsBinding::inflate) {

    private val viewModel: UserStockListingViewModel by viewModels()
    private val userStockAdapter by lazy { UserStockListingAdapter() }

    private var ownedStocks = listOf<UsersStockUiModel>()
    private val symbols = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUsersStocks()
    }

    override fun start() {
        setUpRecycler()
        observer()
    }

    private fun observer() {
        asynchronously {
            viewModel.usersStockState.collectViewState(binding) { stockList ->
                stockList.forEach { symbols.add(it.symbol) }
                d("tag_symbols","$symbols")
            }
        }

        asynchronously {
            viewModel.getUserStocksInformation(symbols)
            viewModel.ownedStocksState.collectViewState(binding){stockInfo->
                d("stockinfo","${stockInfo.data}")
            }
        }
    }

    private fun setUpRecycler() {
        binding.rvWatchlistStocks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWatchlistStocks.adapter = userStockAdapter
    }

}