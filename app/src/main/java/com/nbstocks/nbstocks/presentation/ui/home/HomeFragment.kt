package com.nbstocks.nbstocks.presentation.ui.home


import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.nbstocks.nbstocks.common.extensions.safeSubList
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.presentation.ui.MainActivity
import com.nbstocks.nbstocks.databinding.FragmentHomeBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.home.adapter.WatchlistStocksAdapter
import com.nbstocks.nbstocks.presentation.ui.stock_details.StocksDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val watchlistAdapter by lazy { WatchlistStocksAdapter() }

    override fun start() {
        observer()
        setUpAdapter()
        listeners()

    }

    private fun observer() {
        viewModel.getStockFromWatchlist()

        lifecycleScope.launch {
            viewModel.watchlistStockState.collect {
                watchlistAdapter.submitList(it.data?.reversed()?.safeSubList(5))
            }
        }

    }

    private fun setUpAdapter() {
        binding.rvWatchlist.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            adapter = watchlistAdapter
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
    }

}