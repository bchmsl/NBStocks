package com.nbstocks.nbstocks.presentation.ui.company_listings

import android.graphics.Color
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nbstocks.nbstocks.presentation.ui.MainActivity
import com.nbstocks.nbstocks.databinding.FragmentCompanyListingsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.company_listings.adapter.CompanyListingsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanyListingsFragment : BaseFragment<FragmentCompanyListingsBinding>(FragmentCompanyListingsBinding::inflate) {

    private val viewModel by viewModels<CompanyListingsViewModel>()
    private val stocksAdapter by lazy { CompanyListingsAdapter() }

    override fun start() {
        binding.rvStocks.adapter = stocksAdapter
        observe()
        listeners()

        val activity = requireActivity() as? MainActivity
        activity?.showToolBar()

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

        binding.fabUp.setOnClickListener {
            scrollToTop()
        }


        stocksAdapter.stockItemClicked = {
            findNavController().navigate(CompanyListingsFragmentDirections.actionCompanyListingsFragmentToStocksDetailsFragment())
        }

        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }


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