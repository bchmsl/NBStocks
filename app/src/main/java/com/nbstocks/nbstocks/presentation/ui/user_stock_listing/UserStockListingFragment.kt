package com.nbstocks.nbstocks.presentation.ui.user_stock_listing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.asynchronously
import com.nbstocks.nbstocks.common.extensions.collectViewState
import com.nbstocks.nbstocks.databinding.FragmentOwnStockItemsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserStockListingFragment :
    BaseFragment<FragmentOwnStockItemsBinding>(FragmentOwnStockItemsBinding::inflate) {

    private val viewModel: UserStockListingViewModel by viewModels()

    override fun start() {
        observer()
    }

    private fun observer() {
        asynchronously {
            viewModel.getUsersStocks()
            viewModel.usersStockState.collectViewState(binding) { stockList ->
                d("TAG_Stocks","$stockList")
            }
        }
    }

    private fun setUpRecycler() {

    }

    private fun listeners() {

    }

//    private fun collectOwnedStocks() {
//        asynchronously {
//            watchlistViewModel.ownedStocksState.collectViewState(binding) { watchListStockInfo ->
//                val data = mutableListOf<WatchlistStockInfoUiModel.DataItem>()
//                watchListStockInfo.data.forEachIndexed { index, dataItem ->
//                    ownedStocks.getOrNull(index)?.let {
//                        data.add(
//                            dataItem.copy(
//                                owned = true,
//                                ownedAmount = it.amountInStocks,
//                                ownedPrice = it.price
//                            )
//                        )
//                    }
//                }
//                userStockAdapter.submitList(data.toList())
//            }
//        }
//    }

}