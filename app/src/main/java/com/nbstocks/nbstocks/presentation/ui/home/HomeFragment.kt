package com.nbstocks.nbstocks.presentation.ui.home


import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.nbstocks.nbstocks.common.extensions.makeSnackbar
import com.nbstocks.nbstocks.common.extensions.obtainViewModel
import com.nbstocks.nbstocks.common.extensions.safeSubList
import com.nbstocks.nbstocks.databinding.FragmentHomeBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.common.viewmodel.WatchlistViewModel
import com.nbstocks.nbstocks.presentation.ui.home.adapter.UserStockAdapter
import com.nbstocks.nbstocks.presentation.ui.home.adapter.WatchlistStocksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val watchlistViewModel: WatchlistViewModel by lazy {
        obtainViewModel(
            requireActivity(),
            WatchlistViewModel::class.java,
            defaultViewModelProviderFactory
        )
    }
    private val watchlistAdapter by lazy { WatchlistStocksAdapter() }
    private val userStockAdapter by lazy { UserStockAdapter() }

    override fun start() {
        observer()
        setUpAdapter()
        listeners()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        watchlistViewModel.getItemsFromWatchlist()
        viewModel.getUsersStocks()
    }

    private fun observer() {
        lifecycleScope.launch {
            launch {
                watchlistViewModel.watchlistItemsState.collect { it ->
                    it.data?.let {
                        watchlistViewModel.getWatchlistStocksInformation(it.safeSubList(5))
                    }
                    it.error?.let {
                        it.localizedMessage?.let { it1 -> binding.root.makeSnackbar(it1, true) }
                    }
                }
            }
            launch {
                watchlistViewModel.watchlistStocksState.collect { it ->
                    it.data?.let {
                        watchlistAdapter.submitList(it.data)
                    }
                    it.error?.let {
                        it.localizedMessage?.let { it1 -> binding.root.makeSnackbar(it1, true) }
                    }
                }
            }
            launch { watchlistViewModel.loaderState.collect { binding.pbWatchlist.isVisible = it } }
        }

        lifecycleScope.launch {
            viewModel.usersStockState.collect {
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
            it.symbol?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToStocksDetailsFragment(
                        it
                    )
                )
            }
        }
    }

}