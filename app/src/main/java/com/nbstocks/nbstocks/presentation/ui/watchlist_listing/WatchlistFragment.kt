package com.nbstocks.nbstocks.presentation.ui.watchlist_listing

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.databinding.FragmentWatchlistBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.common.viewmodel.WatchlistViewModel
import com.nbstocks.nbstocks.presentation.ui.watchlist_listing.adapter.WatchlistAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchlistFragment :
    BaseFragment<FragmentWatchlistBinding>(FragmentWatchlistBinding::inflate) {

    private val watchlistViewModel: WatchlistViewModel by lazy {
        obtainViewModel(
            requireActivity(),
            WatchlistViewModel::class.java,
            defaultViewModelProviderFactory
        )
    }
    private val watchlistAdapter by lazy { WatchlistAdapter() }


    override fun start() {
        setUpRecycler()
        observer()
        listeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        watchlistViewModel.getItemsFromWatchlist()
    }

    private fun setUpRecycler() {
        binding.rvWatchlistStocks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = watchlistAdapter
        }
    }

    private fun observer() {
        asynchronously {
            watchlistViewModel.watchlistItemsState.collectViewState(binding) {
                watchlistViewModel.getWatchlistStocksInformation(it.safeSubList(5))
            }
        }
        asynchronously {
            watchlistViewModel.watchlistStocksState.collectViewState(binding) {
                watchlistAdapter.submitList(it.data)
            }

        }
    }

    private fun listeners() {
        watchlistAdapter.stockItemClicked = {
            it.symbol?.let {
                findNavController().navigate(
                    WatchlistFragmentDirections.actionWatchlistFragmentToStocksDetailsFragment(
                        it
                    )
                )
            }
        }
    }

}