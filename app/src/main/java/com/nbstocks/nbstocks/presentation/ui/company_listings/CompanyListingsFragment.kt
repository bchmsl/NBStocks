package com.nbstocks.nbstocks.presentation.ui.company_listings

import android.text.Editable
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbstocks.nbstocks.common.extensions.asynchronously
import com.nbstocks.nbstocks.common.extensions.collectViewState
import com.nbstocks.nbstocks.common.extensions.disable
import com.nbstocks.nbstocks.common.extensions.enable
import com.nbstocks.nbstocks.databinding.FragmentCompanyListingsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.company_listings.adapter.CompanyListingsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyListingsFragment :
    BaseFragment<FragmentCompanyListingsBinding>(FragmentCompanyListingsBinding::inflate) {

    private val viewModel by viewModels<CompanyListingsViewModel>()
    private val stocksAdapter by lazy { CompanyListingsAdapter() }

    override fun start() {
        setUpRecycler()
        observe()
        listeners()
    }



    private fun listeners() {
        binding.apply {
            etSearch.addTextChangedListener { query ->
                searchStock(query)
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
                asynchronously {
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

    private fun searchStock(query: Editable?) {
        asynchronously {
            if (!binding.progressBar.isVisible) {
                viewModel.getCompanyListings(false, query?.let { query.toString() } ?: "") {
                    scrollToTop()
                }
            }
        }
    }

    private fun observe() {
        binding.apply {
            asynchronously {
                viewModel.getCompanyListings(true, "") {
                    tilSearch.enable()
                }
            }
            asynchronously {
                viewModel.loaderState.collect {
                    progressBar.isVisible = it
                }
            }
            asynchronously {
                viewModel.companyListingViewState.collectViewState(binding) {
                    stocksAdapter.submitList(it)
                    binding.rvStocks.startLayoutAnimation()
                }
            }

        }
    }

    private fun setUpRecycler(){
        binding.rvStocks.apply {
            adapter = stocksAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun scrollToTop() {
        binding.rvStocks.scrollToPosition(0)
    }
}