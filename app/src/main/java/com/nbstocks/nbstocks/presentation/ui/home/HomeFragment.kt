package com.nbstocks.nbstocks.presentation.ui.home


import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.databinding.FragmentHomeBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel
import com.nbstocks.nbstocks.presentation.ui.common.viewmodel.WatchlistViewModel
import com.nbstocks.nbstocks.presentation.ui.home.adapter.UserStockAdapter
import com.nbstocks.nbstocks.presentation.ui.home.adapter.WatchlistStocksAdapter
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import dagger.hilt.android.AndroidEntryPoint

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

    private var ownedStocks = listOf<UsersStockUiModel>()

    override fun start() {
        setUpAdapter()
        observer()
        listeners()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        watchlistViewModel.getItemsFromWatchlist()
        viewModel.getUsersStocks()
        viewModel.getBalance()
        viewModel.showBalance(requireContext())
    }

    private fun observer() {
        asynchronously {
            watchlistViewModel.watchlistItemsState.collectViewState(binding) {
                watchlistViewModel.getUserStocksInformation(it.safeSubList(5), true)
            }
        }
        asynchronously {
            watchlistViewModel.watchlistStocksState.collectViewState(binding) {
                watchlistAdapter.submitList(it.data)
                Log.w("TAG", it.toString())
            }
        }
        asynchronously {
            watchlistViewModel.loaderState.collect {
                binding.pbWatchlist.isVisible = it
            }
        }
        asynchronously {
            viewModel.usersStockState.collectViewState(binding) {
                ownedStocks = it
                watchlistViewModel.getUserStocksInformation(
                    ownedStocks.map { it.symbol },
                    false
                )
            }
        }
        asynchronously {
            viewModel.usersStockState.collectViewState(binding) {
                ownedStocks = it
                watchlistViewModel.getUserStocksInformation(
                    ownedStocks.map { it.symbol },
                    false
                )
                collectOwnedStocks()
            }
        }

        asynchronously {
            viewModel.usersBalanceState.collectViewState(binding) { balance ->
                asynchronously {
                    viewModel.balanceShownState.collect { isShown ->
                        when (isShown) {
                            true -> {
                                binding.tvCurrentBalance.text = balance.toDoubleOrNull().toCurrencyString()
                            }
                            else -> {
                                binding.tvCurrentBalance.text = "*****"
                            }
                        }
                    }
                }
            }
        }

    }

    private fun collectOwnedStocks() {
        asynchronously {
            watchlistViewModel.ownedStocksState.collectViewState(binding) { watchListStockInfo ->
                val data = mutableListOf<WatchlistStockInfoUiModel.DataItem>()
                watchListStockInfo.data.forEachIndexed { index, dataItem ->
                    data.add(
                        dataItem.copy(
                            owned = true,
                            ownedAmount = ownedStocks.getOrNull(index)?.amountInStocks,
                            ownedPrice = ownedStocks.getOrNull(index)?.price
                        )
                    )
                }
                userStockAdapter.submitList(data.toList())
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
        binding.btnDeposit.setOnClickListener {
            showConfirmation(binding.tvCurrentBalance.text.toString().toCurrencyDouble(), true)
        }
        binding.btnWithdraw.setOnClickListener {
            showConfirmation(binding.tvCurrentBalance.text.toString().toCurrencyDouble(), false)
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
        userStockAdapter.stockItemClicked = {
            it.symbol?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToStocksDetailsFragment(it)
                )
            }
        }
    }

    private fun showConfirmation(currentBalance: Double, isDeposit: Boolean) {
        val dialog = BalanceDialog(requireContext(), isDeposit)
        dialog.show()
        dialog.confirmCallback = { amount ->
            changeBalance(currentBalance, amount, isDeposit){isTaskSuccessful, message ->
                binding.root.makeSnackbar(message, !isTaskSuccessful)
            }
        }
    }

    private fun changeBalance(
        currentBalance: Double,
        amount: Double,
        isDeposit: Boolean,
        doAfterTask: (isTaskSuccessful: Boolean, message: String) -> Unit
    ){
        if (isDeposit){
            viewModel.setBalance(currentBalance.plus(amount))
            doAfterTask(true, "$amount added to balance.")
        }else{
            if (amount>currentBalance){
                doAfterTask(false, "Not enough money on balance.")
            }else{
                viewModel.setBalance(currentBalance.minus(amount))
                doAfterTask(true, "$amount withdrew from balance.")
            }
        }
    }
}