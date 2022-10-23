package com.nbstocks.nbstocks.presentation.ui.stock_details

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
//import com.anychart.AnyChart
//import com.anychart.chart.common.dataentry.DataEntry
//import com.anychart.chart.common.dataentry.ValueDataEntry
//import com.anychart.charts.Waterfall
import com.nbstocks.nbstocks.presentation.ui.MainActivity
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

        val activity = requireActivity() as? MainActivity
        activity?.hideToolBar()
    }

    private fun observe() {
        viewModel.getStocksDetails(args.stockSymbol)
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
        }
    }

    private fun handleSuccess(stocksList: List<DailyStockUiModel>) {
        stocksList.forEach { stock ->
            Log.d("TAG: STOCK", stock.toString())
        }

        // TODO("Not yet implemented")

        Snackbar.make(binding.root, "Stocks size: ${stocksList.size}", Snackbar.LENGTH_LONG)
            .setBackgroundTint(Color.GREEN).show()


    }

//    private fun loadChart() {
//        val chart = binding.chart
//
//        val waterfall: Waterfall = AnyChart.waterfall()
//
//        waterfall.title("ACME corp. Revenue Flow 2017")
//
//        waterfall.yScale().minimum(0.0)
//
//        waterfall.yAxis(0).labels().format("\${%Value}{scale:(1000000)(1)|(mln)}")
//        waterfall.labels().enabled(true)
//        waterfall.labels().format(
//            """function() {
//      if (this['isTotal']) {
//        return anychart.format.number(this.absolute, {
//          scale: true
//        })
//      }
//
//      return anychart.format.number(this.value, {
//        scale: true
//      })
//    }"""
//        )
//
//        val data: MutableList<DataEntry> = ArrayList()
//        data.add(ValueDataEntry("Start", 23000000))
//        data.add(ValueDataEntry("Jan", 2200000))
//        data.add(ValueDataEntry("Feb", -4600000))
//        data.add(ValueDataEntry("Mar", -9100000))
//        data.add(ValueDataEntry("Apr", 3700000))
//        data.add(ValueDataEntry("May", -2100000))
//        data.add(ValueDataEntry("Jun", 5300000))
//        data.add(ValueDataEntry("Jul", 3100000))
//        data.add(ValueDataEntry("Aug", -1500000))
//        data.add(ValueDataEntry("Sep", 4200000))
//        data.add(ValueDataEntry("Oct", 5300000))
//        data.add(ValueDataEntry("Nov", -1500000))
//        data.add(ValueDataEntry("Dec", 5100000))
//        val end = DataEntry()
//        end.setValue("x", "End")
//        end.setValue("isTotal", true)
//        data.add(end)
//
//        waterfall.data(data)
//
//        chart.setChart(waterfall)
//
//    }


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