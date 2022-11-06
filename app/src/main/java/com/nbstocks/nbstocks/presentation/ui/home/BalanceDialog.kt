package com.nbstocks.nbstocks.presentation.ui.home

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.nbstocks.nbstocks.common.custom_views.CustomDialog
import com.nbstocks.nbstocks.common.extensions.isValid
import com.nbstocks.nbstocks.databinding.DialogDepositWithdrawBinding

class BalanceDialog(context: Context, private val isDeposit: Boolean) : CustomDialog(context) {

    private var _binding: DialogDepositWithdrawBinding? = null
    private val binding get() = _binding!!

    var confirmCallback: ((newBalance: Double) -> Unit)? = null

    override fun onCreateDialog(inflater: LayoutInflater): View {
        _binding = DialogDepositWithdrawBinding.inflate(inflater)
        return binding.root
    }

    override fun onDialogCreated(alertDialog: AlertDialog) {
        start()
    }

    private fun start() {
        if (isDeposit){
            binding.tvDialogTitle.text = "Deposit Money"
            binding.btnDialogConfirm.text = "Deposit"
        }else{
            binding.tvDialogTitle.text = "Withdraw Money"
            binding.btnDialogConfirm.text = "Withdraw"
        }
        binding.btnDialogConfirm.setOnClickListener {
            binding.tilMoney.isValid()?.let {
                it.toDoubleOrNull()?.let { doubleValue ->
                    confirmCallback?.invoke(doubleValue)
                    close()
                }
            }
        }
    }

    override fun close() {
        super.close()
        _binding = null
    }
}