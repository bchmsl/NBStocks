package com.nbstocks.nbstocks.presentation.ui.stock_details

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View

abstract class CustomDialog(val context: Context) {
    private var _dialogBuilder: AlertDialog.Builder? = null
    private val dialogBuilder get() = _dialogBuilder!!

    private var _alertDialog: AlertDialog? = null
    private val alertDialog get() = _alertDialog!!

    fun show(){
        _dialogBuilder = AlertDialog.Builder(context).setView(onCreateDialog(LayoutInflater.from(context)))
        _alertDialog = dialogBuilder.show()
        onDialogCreated(alertDialog)
    }

    abstract fun onCreateDialog(inflater: LayoutInflater): View
    abstract fun onDialogCreated(alertDialog: AlertDialog)

    fun close(){
        alertDialog.cancel()
        _alertDialog = null
        _dialogBuilder = null
    }
}