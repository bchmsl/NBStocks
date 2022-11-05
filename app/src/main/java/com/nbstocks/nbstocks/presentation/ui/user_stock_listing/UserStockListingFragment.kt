package com.nbstocks.nbstocks.presentation.ui.user_stock_listing

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbstocks.nbstocks.common.extensions.asynchronously
import com.nbstocks.nbstocks.common.extensions.collectViewState
import com.nbstocks.nbstocks.databinding.FragmentOwnStockItemsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import com.nbstocks.nbstocks.presentation.ui.user_stock_listing.adapter.UserStockListingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserStockListingFragment :
    BaseFragment<FragmentOwnStockItemsBinding>(FragmentOwnStockItemsBinding::inflate) {

    private val viewModel: UserStockListingViewModel by viewModels()
    private val userStockAdapter by lazy { UserStockListingAdapter() }

    private var ownedStocks = listOf<UsersStockUiModel>()
    private var symbols = listOf<String>()

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
                symbols = stockList.map { it.symbol }
                ownedStocks = stockList
                Log.w("TAG___STOCKS", symbols.toString())
                collectOwnedStocks()
            }
        }


    }

    private fun collectOwnedStocks() {
        asynchronously {
            viewModel.getUserStocksInformation(symbols)
            viewModel.ownedStocksInfoState.collectViewState(binding) { ownedStocksInfo ->
                val data = mutableListOf<WatchlistStockInfoUiModel.DataItem>()
                ownedStocksInfo.data.forEachIndexed { index, dataItem ->
                    ownedStocks.getOrNull(index)?.let {
                        Log.w("T A G", it.toString())
                        data.add(
                            dataItem.copy(
                                owned = true,
                                ownedAmount = it.amountInStocks,
                                ownedPrice = it.price
                            )
                        )
                    }
                }
                Log.w("TAG______", data.toString())
                userStockAdapter.submitList(data.toList())
            }
        }
        asynchronously {
            viewModel.loaderState.collect{
                binding.progressBar.isVisible = it
            }
        }
    }

    private fun setUpRecycler() {
        binding.rvWatchlistStocks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWatchlistStocks.adapter = userStockAdapter
    }

}