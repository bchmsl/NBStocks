package com.nbstocks.nbstocks.presentation.ui.stock_details

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.nbstocks.nbstocks.common.custom_views.CustomDialog
import com.nbstocks.nbstocks.common.extensions.isValid
import com.nbstocks.nbstocks.common.extensions.safeSubString
import com.nbstocks.nbstocks.common.extensions.toPercentString
import com.nbstocks.nbstocks.databinding.DialogBuyStockBinding

class BuySellDialog(
    context: Context,
    private val price: Double,
    private val isBuying: Boolean,
    private val stocksOwned: Double
) :
    CustomDialog(context) {
    private var _binding: DialogBuyStockBinding? = null
    private val binding get() = _binding!!

    var confirmCallback: ((stockAmount: Double?) -> Unit)? = null

    var isMoneyEnabled = true

    override fun onCreateDialog(inflater: LayoutInflater): View {
        _binding = DialogBuyStockBinding.inflate(inflater)
        return binding.root
    }

    override fun onDialogCreated(alertDialog: AlertDialog) {
        start()
    }

    private fun start() {
        binding.tvDialogTitle.text = (if (isBuying) "Buy" else "Sell").plus(" Stock")
        binding.btnDialogConfirm.text = if (isBuying) "Buy" else "Sell"
        binding.apply {
            if (!isBuying) {
                tvStockAmount.visibility = View.VISIBLE
                tvAddAll.visibility = View.VISIBLE
                val tvStockAmountText = if (isMoneyEnabled) {
                    "You have ${
                        stocksOwned.times(price).toString().safeSubString(7)
                    } of this stock owned."
                } else {
                    "You have ${stocksOwned.toString().safeSubString(7)} stocks owned."
                }
                tvStockAmount.text = tvStockAmountText
                tvAddAll.setOnClickListener {
                    if (isMoneyEnabled) {
                        etMoney.setText(stocksOwned.times(price).toString())
                    } else {
                        etStock.setText(stocksOwned.toString())
                    }
                }
            } else {
                tvStockAmount.visibility = View.GONE
                tvAddAll.visibility = View.GONE
            }

            ibtnSwap.setOnClickListener {
                isMoneyEnabled = !isMoneyEnabled
                if (isMoneyEnabled) {
                    tilMoney.visibility = View.VISIBLE
                    tilStock.visibility = View.INVISIBLE
                    tvStockAmount.text = "You have ${
                        stocksOwned.times(price).toString().safeSubString(7)
                    } of this stock owned."
                    etStock.setText("")
                } else if (!isMoneyEnabled) {
                    tilStock.visibility = View.VISIBLE
                    tilMoney.visibility = View.INVISIBLE
                    tvStockAmount.text =
                        "You have ${stocksOwned.toString().safeSubString(7)} stocks owned."
                    etMoney.setText("")
                }
            }

            btnDialogConfirm.setOnClickListener {
                tilStock.isValid()?.let { input ->
                    confirmCallback?.invoke(
                        input.toDoubleOrNull()
                    )
                    close()
                }

                tilMoney.isValid()?.let { input ->
                    confirmCallback?.invoke(
                        input.toDoubleOrNull()?.div(price)
                    )
                    close()
                }
            }
        }
    }
}