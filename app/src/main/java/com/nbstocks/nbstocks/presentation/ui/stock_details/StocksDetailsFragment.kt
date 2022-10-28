package com.nbstocks.nbstocks.presentation.ui.stock_details

//import com.anychart.AnyChart
//import com.anychart.chart.common.dataentry.DataEntry
//import com.anychart.chart.common.dataentry.ValueDataEntry
//import com.anychart.charts.Waterfall
import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Waterfall
import com.google.android.material.snackbar.Snackbar
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.constants.StockPricesRequestFunctions
import com.nbstocks.nbstocks.databinding.FragmentStocksDetailsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.StockPricesUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StocksDetailsFragment :
    BaseFragment<FragmentStocksDetailsBinding>(FragmentStocksDetailsBinding::inflate) {

    private val viewModel: StocksDetailsViewModel by viewModels()

    private val args: StocksDetailsFragmentArgs by navArgs()


    override fun start() {
        listeners()
        observe()
    }

    private fun observe() {

        viewModel.getStocksDetails(args.stockSymbol, StockPricesRequestFunctions.TIME_SERIES_DAILY)

        viewModel.getCurrentStock(args.stockSymbol)

        lifecycleScope.launch {
//            launch { viewModel.loaderState.collect { progressBar.isVisible = it } }
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
                    it.data?.let { stocksList ->
                        binding.apply {
                            tvPrice.text = it.data?.price
                            tvSymbol.text = it.data?.symbol
                            tvPercentage.text = it.data?.changePercent
                            tvOverviewSymbol.text = it.data?.symbol
                            tvCurrentPrice.text = it.data?.price
                            tvLowPrice.text = it.data?.low
                            tvHighPrice.text = it.data?.high
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

        val chart = binding.chart
        val waterfall: Waterfall = AnyChart.waterfall()

        val data: MutableList<DataEntry> = ArrayList()

        for (i in stocksList) {
            if (data.size < 14) {
                data.add(
                    ValueDataEntry(
                        i.timestamp,
                        ((i.close)!!.toDouble() - (i.open)!!.toDouble())
                    )
                )
            } else {
                break
            }
        }

        waterfall.yScale().minimum(0.0)
        waterfall.labels().enabled(false)

        val set = com.anychart.data.Set.instantiate()
        set.data(data)

        val series: com.anychart.core.waterfall.series.Waterfall = waterfall.waterfall(set, "")

        series.normal().fallingFill("#FB3E64", 1.0)
//        series.normal().fallingStroke("#FB3E64", 1, "10 5", "round", "null");

        series.normal().risingFill("#5DE066", 1.0)
//        series.normal().risingStroke("#5DE066", 1, "10 5", "round", "null");


        waterfall.data(data)

        chart.setChart(waterfall)

    }

    private fun addWatchlistStock(currentStockUiModel: CurrentStockUiModel) {
        viewModel.addStockInWatchlist(currentStockUiModel)
    }

    private fun removeWatchlistStock(currentStockUiModel: CurrentStockUiModel){
        viewModel.removeStockInWatchlist(currentStockUiModel)
    }


    private fun listeners() {
        binding.btnDaily.setOnClickListener { }
        binding.btnMonthly.setOnClickListener { }


        binding.btnAddToWatchlist.apply {
            setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.currentStockState.collect {
                        it.data?.let { it1 -> addWatchlistStock(it1) }
                    }
                }
                setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        }


        binding.btnBuy.setOnClickListener {
            showConfirmation()
        }

        binding.btnSell.setOnClickListener {
            showConfirmation()
        }

        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    private fun showConfirmation() {
        val animation = AnimationUtils.loadAnimation(
            requireContext(),
            androidx.transition.R.anim.abc_slide_in_top
        )
        binding.btnSell.visibility = View.INVISIBLE
        binding.btnBuy.visibility = View.INVISIBLE
        binding.vConfirmBuySell.visibility = View.VISIBLE
        binding.vConfirmBuySell.startAnimation(animation)
        binding.etCash.visibility = View.VISIBLE
        binding.etCash.startAnimation(animation)
        binding.etCashInputLayout.visibility = View.VISIBLE
        binding.etCashInputLayout.startAnimation(animation)
        binding.tvResult.visibility = View.VISIBLE
        binding.tvResult.startAnimation(animation)
        binding.buttonsLinear.visibility = View.VISIBLE
        binding.buttonsLinear.startAnimation(animation)
    }

}