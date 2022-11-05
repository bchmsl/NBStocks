package com.nbstocks.nbstocks.presentation.ui.home


import android.os.Bundle
import android.util.Log.d
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
    private var userBalance: Double? = 0.0

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
                                userBalance = balance.toDoubleOrNull()
                            }
                            else -> {
                                binding.tvCurrentBalance.text = "*****"
                                userBalance = balance.toDoubleOrNull()
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
                    ownedStocks.getOrNull(index)?.let {
                        data.add(
                            dataItem.copy(
                                owned = true,
                                ownedAmount = it.amountInStocks,
                                ownedPrice = it.price
                            )
                        )
                    }
                }
                userStockAdapter.submitList(data.safeSubList(5).toList())
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
            userBalance?.let { it1 -> showConfirmation(it1, true) }
        }
        binding.btnWithdraw.setOnClickListener {
            userBalance?.let { it1 -> showConfirmation(it1, false) }
        }
        binding.tvWatchlistSeeAll.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWatchlistFragment())
        }
        binding.tvYourStocksSeeAll.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserStockListingFragment())
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
        binding.tbtnProfile.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
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