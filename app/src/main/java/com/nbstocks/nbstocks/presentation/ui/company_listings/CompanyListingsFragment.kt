package com.nbstocks.nbstocks.presentation.ui.company_listings

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nbstocks.nbstocks.common.extensions.disable
import com.nbstocks.nbstocks.common.extensions.enable
import com.nbstocks.nbstocks.common.extensions.makeSnackbar
import com.nbstocks.nbstocks.databinding.FragmentCompanyListingsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.company_listings.adapter.CompanyListingsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanyListingsFragment :
    BaseFragment<FragmentCompanyListingsBinding>(FragmentCompanyListingsBinding::inflate) {

    private val viewModel by viewModels<CompanyListingsViewModel>()
    private val stocksAdapter by lazy { CompanyListingsAdapter() }

    override fun start() {
        binding.rvStocks.adapter = stocksAdapter
        observe()
        listeners()
    }

    private fun listeners() {
        binding.apply {
            etSearch.addTextChangedListener { query ->
                lifecycleScope.launch {
                    if (!progressBar.isVisible) {
                        viewModel.getCompanyListings(false, query?.let { query.toString() } ?: "") {
                            scrollToTop()
                        }
                    }
                }.invokeOnCompletion {
                    scrollToTop()
                }
                if (query.toString().isBlank()) {
                    scrollToTop()
                }
            }

            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
                tilSearch.apply {
                    disable()
                    editText?.setText("")

                }
                lifecycleScope.launch {
                    viewModel.getCompanyListings(true, null) {
                        tilSearch.enable()
                    }
                }
                scrollToTop()
            }

            fabUp.setOnClickListener {
                scrollToTop()
            }


            stocksAdapter.stockItemClicked = {
                it.symbol?.let { symbol ->
                    findNavController().navigate(
                        CompanyListingsFragmentDirections.actionCompanyListingsFragmentToStocksDetailsFragment(
                            symbol
                        )
                    )
                }
            }
        }
    }

    private fun observe() {
        binding.apply {
            lifecycleScope.apply {
                launch {
                    viewModel.getCompanyListings(true, "") {
                        tilSearch.enable()
                    }
                }

                launch {
                    viewModel.loaderState.collect {
                        progressBar.isVisible = it
                    }
                }

                launch {
                    viewModel.viewState.collect {
                        it.data?.let { stocks ->
                            stocksAdapter.submitList(stocks)
                        }
                        it.error?.let { error ->
                            error.localizedMessage?.let { message -> root.makeSnackbar(message, true) }
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