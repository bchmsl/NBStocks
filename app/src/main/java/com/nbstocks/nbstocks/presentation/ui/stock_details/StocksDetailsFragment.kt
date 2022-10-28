package com.nbstocks.nbstocks.presentation.ui.stock_details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.google.android.material.snackbar.Snackbar
import com.nbstocks.nbstocks.common.extensions.currentTab
import com.nbstocks.nbstocks.common.extensions.onTabSelected
import com.nbstocks.nbstocks.common.extensions.toMonthDay
import com.nbstocks.nbstocks.databinding.FragmentStockDetailsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.StockPricesUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.String


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

        viewModel.getStocksDetails(args.stockSymbol, binding.tlSwitchStocks.currentTab)

        viewModel.getCurrentStock(args.stockSymbol)

        lifecycleScope.launch {
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


    private fun listeners() {
        binding.btnBuy.setOnClickListener {
            showConfirmation()
        }

        binding.btnSell.setOnClickListener {
            showConfirmation()
        }

        binding.ibtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tlSwitchStocks.onTabSelected {
            viewModel.getStocksDetails(args.stockSymbol, binding.tlSwitchStocks.currentTab)
        }
    }

    private fun setupChart() {
        val chart = binding.chart
        val waterfall = AnyChart.waterfall()
        waterfall.yScale().minimum(0.0)
        waterfall.labels().enabled(false)

        set = com.anychart.data.Set.instantiate()
        val series = waterfall.waterfall(set, "")

        series.normal().fallingFill("#FB3E64", 1.0)
        series.normal().fallingStroke("#FB3E64", 1, "null", "round", "null");
        series.normal().risingFill("#5DE066", 1.0)
        series.normal().risingStroke("#5DE066", 1, "null", "round", "null");

        val layout = binding.root
        val viewColor = layout.background as ColorDrawable
        val colorId = viewColor.color
        val hexColor = String.format("#%06X", 0xFFFFFF and colorId)

        waterfall.background().enabled(true).fill(hexColor)

        chart.setProgressBar(binding.progressBar)
        chart.setChart(waterfall)
    }

    private fun setDataToChart(stocksList: List<StockPricesUiModel>){
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

    private fun showConfirmation() {
//        val animation = AnimationUtils.loadAnimation(
//            requireContext(),
//            androidx.transition.R.anim.abc_slide_in_top
//        )
//        binding.btnSell.visibility = View.INVISIBLE
//        binding.btnBuy.visibility = View.INVISIBLE
//        binding.vConfirmBuySell.visibility = View.VISIBLE
//        binding.vConfirmBuySell.startAnimation(animation)
//        binding.etCash.visibility = View.VISIBLE
//        binding.etCash.startAnimation(animation)
//        binding.etCashInputLayout.visibility = View.VISIBLE
//        binding.etCashInputLayout.startAnimation(animation)
//        binding.tvResult.visibility = View.VISIBLE
//        binding.tvResult.startAnimation(animation)
//        binding.buttonsLinear.visibility = View.VISIBLE
//        binding.buttonsLinear.startAnimation(animation)
    }
}