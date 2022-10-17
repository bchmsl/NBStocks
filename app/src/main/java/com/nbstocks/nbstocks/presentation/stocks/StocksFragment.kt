package com.nbstocks.nbstocks.presentation.stocks

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.databinding.ActivityMainBinding
import com.nbstocks.nbstocks.databinding.FragmentStocksBinding
import com.nbstocks.nbstocks.presentation.base.BaseFragment
import com.nbstocks.nbstocks.presentation.stocks.adapter.StocksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StocksFragment : BaseFragment<FragmentStocksBinding>(FragmentStocksBinding::inflate) {

    private val viewModel by viewModels<StocksViewModel>()
    private val stocksAdapter by lazy { StocksAdapter() }

    override fun start() {
        binding.rvStocks.adapter = stocksAdapter
        observe()
        listeners()
    }

    private fun listeners() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false


            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    if (!binding.progressBar.isVisible) {
                        viewModel.getCompanyListings(false, newText ?: "")
                    }
                }.invokeOnCompletion {
                    scrollToTop()
                }
                if (newText.isNullOrBlank()){
                    scrollToTop()
                }
                return true
            }

        })


        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            lifecycleScope.launch {
                viewModel.getCompanyListings(true, null)
            }.invokeOnCompletion {
                scrollToTop()
            }
        }

//        binding.fabUp.setOnClickListener {
//            scrollToTop()
//        }
    }

    private fun observe() {
        viewModel.getCompanyListings(true, null)
        lifecycleScope.launch {
            binding.apply {
                launch { viewModel.loaderState.collect { progressBar.isVisible = it } }

                launch {
                    viewModel.viewState.collect {
                        it.data?.let { stocks ->
                            stocksAdapter.submitList(stocks)
                        }
                        it.error?.let { error ->
                            Snackbar.make(root, error.localizedMessage ?: "", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(Color.RED).show()
                        }
                    }
                }
            }
        }
    }

    private fun scrollToTop() {
        binding.rvStocks.scrollToPosition(0)
    }
}