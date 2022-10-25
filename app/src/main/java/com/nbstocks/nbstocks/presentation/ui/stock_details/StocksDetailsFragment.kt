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
import com.nbstocks.nbstocks.databinding.FragmentStocksDetailsBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.DailyStockUiModel
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
        viewModel.getStocksDetails(args.stockSymbol)
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
                viewModel.currentStockState.collect{
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
            }
        }
    }

    private fun handleSuccess(stocksList: List<DailyStockUiModel>) {

        val chart = binding.chart
        val waterfall: Waterfall = AnyChart.waterfall()

        waterfall.yScale().minimum(0.0)
        waterfall.labels().enabled(true)

        val data: MutableList<DataEntry> = ArrayList()

        for (i in stocksList) {
            if (data.size < 7) {
                data.add(ValueDataEntry(i.timestamp, i.high?.toDouble()))
            } else {
                break
            }
        }

        waterfall.yScale().minimum(0.0)
        waterfall.yAxis(0).labels().format("\${%Value}{scale:(1000000)(1)|(mln)}")
        waterfall.labels().enabled(true)
        waterfall.labels().format(
            """function() {
                if (this['isTotal']) {
                    return anychart.format.number(this.absolute, {
                    scale: true
                    })
                }

                return anychart.format.number(this.value, {
                scale: true
                })
            }"""
        )


        waterfall.data(data)

        chart.setChart(waterfall)

    }


    private fun listeners() {
        binding.btnDaily.setOnClickListener { }
        binding.btnMonthly.setOnClickListener { }

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