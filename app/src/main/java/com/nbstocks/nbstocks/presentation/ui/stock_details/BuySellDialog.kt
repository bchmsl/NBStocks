package com.nbstocks.nbstocks.presentation.ui.stock_details

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.nbstocks.nbstocks.common.extensions.isValid
import com.nbstocks.nbstocks.databinding.DialogBuyStockBinding

class BuySellDialog(
    context: Context,
    private val price: Double,
    private val isBuying: Boolean,
) :
    CustomDialog(context) {
    private var _binding: DialogBuyStockBinding? = null
    private val binding get() = _binding!!

    var confirmCallback: ((stockAmount: Double?) -> Unit)? = null

    override fun onCreateDialog(inflater: LayoutInflater): View {
        _binding = DialogBuyStockBinding.inflate(inflater)
        return binding.root
    }

    override fun onDialogCreated(alertDialog: AlertDialog) {
        start()
    }

    private fun start() {
        binding.tvDialogTitle.text = (if (isBuying) "Buy" else "Sell") + " Stock"
        binding.btnDialogConfirm.text = if (isBuying) "Buy" else "Sell"

        binding.apply {
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