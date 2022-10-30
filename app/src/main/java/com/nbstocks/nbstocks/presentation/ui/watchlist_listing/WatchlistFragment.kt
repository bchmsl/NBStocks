package com.nbstocks.nbstocks.presentation.ui.watchlist_listing

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbstocks.nbstocks.databinding.FragmentWatchlistBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.watchlist_listing.adapter.WatchlistAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchlistFragment : BaseFragment<FragmentWatchlistBinding>(FragmentWatchlistBinding::inflate) {

    private val viewModel: WatchlistViewModel by viewModels()
    private val watchlistAdapter by lazy { WatchlistAdapter() }


    override fun start() {
        setUpRecycler()
        observer()
        listeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getStockFromWatchlist()
    }

    private fun setUpRecycler(){
        binding.rvWatchlistStocks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = watchlistAdapter
        }
    }

    private fun observer(){
        lifecycleScope.launch{
            viewModel.watchlistStockState.collect{
                watchlistAdapter.submitList(it.data)
            }
        }
    }

    private fun listeners(){
        watchlistAdapter.stockItemClicked = {
            findNavController().navigate(WatchlistFragmentDirections.actionWatchlistFragmentToStocksDetailsFragment(it.symbol))
        }
    }

}