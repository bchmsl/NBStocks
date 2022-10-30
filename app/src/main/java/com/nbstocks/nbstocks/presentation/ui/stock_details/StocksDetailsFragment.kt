package com.nbstocks.nbstocks.presentation.ui.stock_details

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.google.android.material.snackbar.Snackbar
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.currentTab
import com.nbstocks.nbstocks.common.extensions.isValid
import com.nbstocks.nbstocks.common.extensions.makeSnackbar
import com.nbstocks.nbstocks.common.extensions.toMonthDay
import com.nbstocks.nbstocks.databinding.FragmentStockDetailsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.StockPricesUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StocksDetailsFragment :
    BaseFragment<FragmentStockDetailsBinding>(FragmentStockDetailsBinding::inflate) {

    private val viewModel: StocksDetailsViewModel by viewModels()
    private val args: StocksDetailsFragmentArgs by navArgs()
    private lateinit var set: com.anychart.data.Set


    override fun start() {
        setupChart()
        listeners()
        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.getStocksDetails(args.stockSymbol, binding.tlSwitchStocks.currentTab)
            launch { viewModel.loaderState.collect { binding.progressBar.isVisible = it } }
            launch {
                viewModel.viewState.collect {
                    it.data?.let { stocksList ->
                        handleSuccess(stocksList)
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
                            tvPrice.text = stock.price
                            tvSymbol.text = stock.symbol
                            tvPercentage.text = stock.changePercent
                            tvTitleName.text = stock.symbol
                            tvCurrentPrice.text = stock.price
                            tvLowPrice.text = stock.low
                            tvHighPrice.text = stock.high
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


    private fun handleSuccess(stocksList: List<StockPricesUiModel>) {
        setDataToChart(stocksList)
    }

    private fun setupChart() {
        val chart = binding.chart
        val waterfall = AnyChart.waterfall()
        waterfall.yScale().minimum(0.0)
        waterfall.labels().enabled(false)

        set = com.anychart.data.Set.instantiate()
        val series = waterfall.waterfall(set, "")

        series.normal().fallingFill("#FB3E64", 1.0)
        series.normal().fallingStroke("#FB3E64", 1, "null", "round", "null")
        series.normal().risingFill("#5DE066", 1.0)
        series.normal().risingStroke("#5DE066", 1, "null", "round", "null")

        val layout = binding.root
        val viewColor = layout.background as ColorDrawable
        val colorId = viewColor.color
        val hexColor = String.format("#%06X", 0xFFFFFF and colorId)

        waterfall.background().enabled(true).fill(hexColor)

        chart.setProgressBar(binding.progressBar)
        chart.setChart(waterfall)
    }

    private fun setDataToChart(stocksList: List<StockPricesUiModel>) {
        val data = mutableListOf<DataEntry>()
        for (i in stocksList) {
            if (data.size < 14) {
                data.add(
                    ValueDataEntry(
                        i.timestamp?.toMonthDay(),
                        ((i.close)!!.toDouble() - (i.open)!!.toDouble())
                    )
                )
            } else {
                break
            }
        }
        set.data(data)
    }


    private fun addWatchlistStock(currentStockUiModel: CurrentStockUiModel) {
        viewModel.addStockInWatchlist(currentStockUiModel)
    }

    private fun removeWatchlistStock(currentStockUiModel: CurrentStockUiModel) {
        viewModel.removeStockInWatchlist(currentStockUiModel)
    }


    private fun listeners() {
        binding.tbFavorite.setOnCheckedChangeListener { buttonView, isFavoriteChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.currentStockState.collect {
                    it.data?.let { stock ->
                        if (isFavoriteChecked) {
                            addWatchlistStock(stock)
                        } else {
                            removeWatchlistStock(stock)
                        }
                    }
                }
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
        val dialogLayout =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_buy_stock, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogLayout)
        val alertDialog = dialogBuilder.show()

        val tvTitle = dialogLayout.findViewById<TextView>(R.id.tvDialogTitle)
        val etMoney = dialogLayout.findViewById<EditText>(R.id.etMoney)
        val etStock = dialogLayout.findViewById<EditText>(R.id.etStock)
        val btnConfirm = dialogLayout.findViewById<Button>(R.id.btnDialogConfirm)

        tvTitle.text = (if (isBuying) "Buy" else "Sell")+" Stock"
        btnConfirm.text = if (isBuying) "Buy" else "Sell"

        btnConfirm.setOnClickListener {
            if (etStock.isValid()) {
                confirm(etStock.text.toString().toDoubleOrNull(), isBuying){isTaskSuccessful, message ->
                    alertDialog.cancel()
                    binding.root.makeSnackbar(message, isTaskSuccessful)
                }
            }else if (etMoney.isValid()){
                confirm(etMoney.text.toString().toDoubleOrNull()?.div(price), isBuying){isTaskSuccessful, message ->
                    alertDialog.cancel()
                    binding.root.makeSnackbar(message, isTaskSuccessful)
                }
            }
        }
//        etMoney.addTextChangedListener {
//            etStock.setText(
//                (it.toString().toDouble() / price).toString()
//            )
//        }
//        etStock.addTextChangedListener {
//            etMoney.setText(
//                (it.toString().toDouble() * price).toString()
//            )
//        }
    }

    private fun confirm(
        amountOfStock: Double?,
        isBuying: Boolean,
        doAfterTask: (isTaskSuccessful: Boolean, message: String) -> Unit
    ) {
        if (isBuying){
            buyStock(amountOfStock){isTaskSuccessful, message ->
                doAfterTask(isTaskSuccessful, message)
            }
        }else{
            sellStock(amountOfStock){isTaskSuccessful, message ->
                doAfterTask(isTaskSuccessful, message)
            }
        }
    }

    private fun buyStock(
        amountOfStock: Double?,
        doAfterTask: (isTaskSuccessful: Boolean, message: String) -> Unit
    ) {
        //TODO("Add to database")

        doAfterTask(true, "$amountOfStock stocks bought successfully!")
    }

    private fun sellStock(
        amountOfStock: Double?,
        doAfterTask: (isTaskSuccessful: Boolean, message: String) -> Unit
    ) {
        //TODO("Modify in database")

        doAfterTask(true, "$amountOfStock stocks sold successfully")
    }

}