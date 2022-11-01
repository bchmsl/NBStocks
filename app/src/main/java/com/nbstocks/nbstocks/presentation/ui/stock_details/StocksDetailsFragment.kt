package com.nbstocks.nbstocks.presentation.ui.stock_details

import android.graphics.drawable.ColorDrawable
import android.util.Log.d
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.databinding.FragmentStockDetailsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.IntervalStockPricesUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class StocksDetailsFragment :
    BaseFragment<FragmentStockDetailsBinding>(FragmentStockDetailsBinding::inflate) {

    private val viewModel: StocksDetailsViewModel by viewModels()
    private val args: StocksDetailsFragmentArgs by navArgs()
    private val stockPricesChart by lazy { StockPricesChart() }
    private var amount: Double = 0.0

    override fun start() {
        setupChart()
        listeners()
        observe()
    }

    private fun setupChart() {
        val viewColor = binding.root.background as ColorDrawable
        val hexColor = String.format("#%06X", 0xFFFFFF and viewColor.color)
        stockPricesChart.apply {
            backgroundColor = hexColor
            initChart()
        }
        binding.chart.apply {
            setBackgroundColor(hexColor)
            setChart(stockPricesChart.waterfall)
            setProgressBar(binding.pbChartLoader)
        }
        binding.tlSwitchStocks.doSelectedTask { range, interval ->
            viewModel.getStocksDetails(args.stockSymbol, range, interval)
        }
    }

    private fun observe() {
        asynchronously {
            viewModel.loaderState.collect {
                binding.progressBar.isVisible = it
            }
        }

        asynchronously {
            viewModel.intervalStockPricesViewState.collectViewState(binding) {
                handleIntervalSuccess(it)
            }
        }
        asynchronously {
            viewModel.getCurrentStock(args.stockSymbol)
            viewModel.currentStockViewState.collectViewState(binding) {
                handleCurrentStockSuccess(it)
            }
        }

        asynchronously {
            viewModel.getStockAmount(args.stockSymbol)
        }

        asynchronously {
            viewModel.amountOfStock.collect {
                amount = it.data?.toDouble() ?: 0.0
            }
        }

    }

    private fun handleCurrentStockSuccess(currentStockUiModel: CurrentStockUiModel) {
        binding.apply {
            with(currentStockUiModel) {
                tvCurrentPrice.text = currentPrice?.raw.toCurrencyString()
                tvPrice.text = currentPrice?.raw.toCurrencyString()
                tvSymbol.text = symbol
                tvLowPrice.text = targetLowPrice?.raw.toCurrencyString()
                tvHighPrice.text = targetHighPrice?.raw.toCurrencyString()
                tvPercentage.text = revenueGrowth?.raw.toPercentString()
                tvTitleName.text = symbol
            }
        }
    }

    private fun handleIntervalSuccess(stockModel: IntervalStockPricesUiModel) {
        stockPricesChart.submitData(stockModel.data)
    }

    private fun addWatchlistStock(symbol: String) {
        viewModel.addStockInWatchlist(symbol)
    }

    private fun removeWatchlistStock(symbol: String) {
        viewModel.removeStockInWatchlist(symbol)
    }

    private fun listeners() {
        binding.tbFavorite.setOnCheckedChangeListener { _, isFavoriteChecked ->
            if (isFavoriteChecked) {
                addWatchlistStock(binding.tvSymbol.text.toString())
            } else {
                removeWatchlistStock(binding.tvSymbol.text.toString())
            }
        }
        binding.tlSwitchStocks.onTabSelected {
            binding.tlSwitchStocks.doSelectedTask { range, interval ->
                viewModel.getStocksDetails(args.stockSymbol, range, interval)
            }
        }

        binding.btnBuy.setOnClickListener {
            showConfirmation(binding.tvPrice.text.toString().toCurrencyDouble(), true)
        }
        binding.btnSell.setOnClickListener {
            showConfirmation(binding.tvPrice.text.toString().toCurrencyDouble(), false)
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


        if (amount == 0.0) {
            viewModel.buyStockToOwner(
                UsersStockUiModel(
                    symbol = binding.tvSymbol.text.toString(),
                    price = binding.tvCurrentPrice.text.toString(),
                    amountInStocks = amountOfStock
                )
            )
        } else {
            viewModel.buyStockToOwner(
                UsersStockUiModel(
                    symbol = binding.tvSymbol.text.toString(),
                    price = binding.tvCurrentPrice.text.toString(),
                    amountInStocks = amountOfStock?.plus(amount)
                )
            )
        }


        doAfterTask(true, "$amountOfStock stocks bought successfully!")
    }

    private fun sellStock(
        amountOfStock: Double?,
        doAfterTask: (isTaskSuccessful: Boolean, message: String) -> Unit
    ) {

        if (amount == 0.0 || amount < amountOfStock!!) {
            binding.root.makeSnackbar("Not enough Balance", true)
        } else {
            viewModel.buyStockToOwner(
                UsersStockUiModel(
                    symbol = binding.tvSymbol.text.toString(),
                    price = binding.tvCurrentPrice.text.toString(),
                    amountInStocks = amount.minus(amountOfStock)
                )
            )
        }

        doAfterTask(true, "$amountOfStock stocks sold successfully")

    }
}