package com.nbstocks.nbstocks.presentation.ui.profile

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.nbstocks.nbstocks.common.custom_views.CustomDialog
import com.nbstocks.nbstocks.databinding.DialogAboutBinding

class AboutDialog(context: Context) : CustomDialog(context) {

    private var _binding: DialogAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(inflater: LayoutInflater): View {
        _binding = DialogAboutBinding.inflate(inflater)
        return binding.root
    }

    override fun onDialogCreated(alertDialog: AlertDialog) {
        binding.ibtnClose.setOnClickListener {
            this.close()
        }
    }

    override fun close() {
        super.close()
        _binding = null
    }
}