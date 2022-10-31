package com.nbstocks.nbstocks.presentation.ui.stock_details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nbstocks.nbstocks.common.extensions.doWhenSelected
import com.nbstocks.nbstocks.common.extensions.makeSnackbar
import com.nbstocks.nbstocks.common.extensions.onTabSelected
import com.nbstocks.nbstocks.databinding.FragmentStockDetailsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.IntervalStockPricesUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StocksDetailsFragment :
    BaseFragment<FragmentStockDetailsBinding>(FragmentStockDetailsBinding::inflate) {

    private val viewModel: StocksDetailsViewModel by viewModels()
    private val args: StocksDetailsFragmentArgs by navArgs()
    private val stockPricesChart by lazy { StockPricesChart() }

    override fun start() {
        setupChart()
        listeners()
        observe()
    }

    private fun setupChart() {
        val viewColor = binding.root.background as ColorDrawable
        val hexColor = String.format("#%06X", 0xFFFFFF and viewColor.color)
        stockPricesChart.backgroundColor = hexColor
        stockPricesChart.initChart()
        binding.chart.apply {
            setBackgroundColor(hexColor)
            setChart(stockPricesChart.waterfall)
            setProgressBar(binding.pbChartLoader)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            binding.tlSwitchStocks.doWhenSelected { range, interval ->
                viewModel.getStocksDetails(args.stockSymbol, range, interval)
            }
            launch { viewModel.loaderState.collect { binding.progressBar.isVisible = it } }
            launch {
                viewModel.viewState.collect {
                    it.data?.let { stockModel ->
                        handleSuccess(stockModel)
                    }
                    it.error?.let { error ->
                        Snackbar.make(
                            binding.root,
                            error.localizedMessage ?: "",
                            Snackbar.LENGTH_LONG
                        )
                            .setBackgroundTint(Color.RED).show()
                    }
                }
            }
            launch {
                viewModel.getCurrentStock(args.stockSymbol)
                viewModel.currentStockState.collect {
                    it.data?.let { stock ->
                        binding.apply {
                            Log.d("TAG", stock.toString())
                        }
                    }
                    it.error?.let { error ->
                        Snackbar.make(
                            binding.root,
                            error.localizedMessage ?: "",
                            Snackbar.LENGTH_LONG
                        )
                            .setBackgroundTint(Color.RED).show()
                    }
                }
            }
        }
    }


    private fun handleSuccess(stockModel: IntervalStockPricesUiModel) {
        stockPricesChart.submitData(stockModel.data)
    }


    private fun addWatchlistStock(symbol: String) {
        viewModel.addStockInWatchlist(symbol)
    }

    private fun removeWatchlistStock(symbol: String) {
        viewModel.removeStockInWatchlist(symbol)
    }


    private fun listeners() {
        binding.tbFavorite.setOnCheckedChangeListener { buttonView, isFavoriteChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.currentStockState.collect {
                    it.data?.let { stock ->
                        if (isFavoriteChecked) {
                            addWatchlistStock(stock.symbol)
                        } else {
                            removeWatchlistStock(stock.symbol)
                        }
                    }
                }
            }
        }
        binding.tlSwitchStocks.onTabSelected {
            binding.tlSwitchStocks.doWhenSelected { range, interval ->
                viewModel.getStocksDetails(args.stockSymbol, range, interval)
            }
        }
        binding.btnBuy.setOnClickListener {
            showConfirmation(binding.tvPrice.text.toString().toDouble(), true)
        }
        binding.btnSell.setOnClickListener {
            showConfirmation(binding.tvPrice.text.toString().toDouble(), false)
        }
        binding.ibtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun showConfirmation(price: Double, isBuying: Boolean) {
        val dialog = BuySellDialog(requireContext(), price, isBuying)
        dialog.show()

        dialog.confirmCallback = { stockAmount ->
            confirm(stockAmount, isBuying) { isTaskSuccessful, message ->
                binding.root.makeSnackbar(message, !isTaskSuccessful)
            }
        }
    }

    private fun confirm(
        amountOfStock: Double?,
        isBuying: Boolean,
        doAfterTask: (isTaskSuccessful: Boolean, message: String) -> Unit
    ) {
        if (isBuying) {
            buyStock(amountOfStock) { isTaskSuccessful, message ->
                doAfterTask(isTaskSuccessful, message)
            }
        } else {
            sellStock(amountOfStock) { isTaskSuccessful, message ->
                doAfterTask(isTaskSuccessful, message)
            }
        }
    }

    private fun buyStock(
        amountOfStock: Double?,
        doAfterTask: (isTaskSuccessful: Boolean, message: String) -> Unit
    ) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.buyStockToOwner(
                UsersStockUiModel(
                    symbol = binding.tvSymbol.text.toString(),
                    price = binding.tvCurrentPrice.text.toString(),
                    amountInStocks = amountOfStock.toString())
            )
        }

        doAfterTask(true, "$amountOfStock stocks bought successfully!")

    }

    private fun sellStock(
        amountOfStock: Double?,
        doAfterTask: (isTaskSuccessful: Boolean, message: String) -> Unit
    ) {

        // TODO("Change in database")
        doAfterTask(true, "$amountOfStock stocks sold successfully")

    }
}