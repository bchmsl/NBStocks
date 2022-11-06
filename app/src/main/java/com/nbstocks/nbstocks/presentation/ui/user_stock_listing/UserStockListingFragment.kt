package com.nbstocks.nbstocks.presentation.ui.user_stock_listing

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var symbols = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUsersStocks()
    }

    override fun start() {
        setUpRecycler()
        observer()
        listeners()
    }

    private fun listeners() {
        userStockAdapter.stockItemClicked = {dataItem ->
            dataItem.symbol?.let {
                findNavController().navigate(
                    UserStockListingFragmentDirections.actionUserStockListingFragmentToStocksDetailsFragment(
                        it, dataItem.regularMarketChangePercent?.toFloat() ?: 0.0f
                    )
                )
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            viewModel.getUsersStocks()
        }
        binding.ibtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observer() {
        asynchronously {
            viewModel.usersStockState.collectViewState(binding) { stockList ->
                symbols = stockList.map { it.symbol }
                ownedStocks = stockList
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
                        data.add(
                            dataItem.copy(
                                owned = true,
                                ownedAmount = it.amountInStocks,
                                ownedPrice = it.price
                            )
                        )
                    }
                }
                userStockAdapter.submitList(data.toList())
                binding.rvWatchlistStocks.startLayoutAnimation()
            }
        }
        asynchronously {
            viewModel.loaderState.collect {
                binding.progressBar.isVisible = it
            }
        }
    }

    private fun setUpRecycler() {
        binding.rvWatchlistStocks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWatchlistStocks.adapter = userStockAdapter
    }

}