package com.nbstocks.nbstocks.presentation.ui.home


import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.nbstocks.nbstocks.common.extensions.safeSubList
import com.nbstocks.nbstocks.databinding.FragmentHomeBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.home.adapter.UserStockAdapter
import com.nbstocks.nbstocks.presentation.ui.home.adapter.WatchlistStocksAdapter
import com.nbstocks.nbstocks.presentation.ui.watchlist_listing.adapter.WatchlistAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val watchlistAdapter by lazy { WatchlistStocksAdapter() }
    private val userStockAdapter by lazy { UserStockAdapter() }

    override fun start() {
        observer()
        setUpAdapter()
        listeners()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getStockFromWatchlist()
        viewModel.getUsersStocks()
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.watchlistStockState.collect {
                watchlistAdapter.submitList(it.data?.safeSubList(5))
            }
        }

        lifecycleScope.launch {
            viewModel.usersStockState.collect{
                userStockAdapter.submitList(it.data?.safeSubList(5))
            }
        }

    }

    private fun setUpAdapter() {
        binding.rvWatchlist.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            adapter = watchlistAdapter
        }

        binding.rvYourStocks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userStockAdapter
        }
    }

    private fun listeners() {
        binding.btnBuy.setOnClickListener {

        }
        binding.btnSell.setOnClickListener {

        }
        binding.tvWatchlistSeeAll.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWatchlistFragment())
        }
        watchlistAdapter.stockItemClicked = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStocksDetailsFragment(it.symbol))
        }
    }

}