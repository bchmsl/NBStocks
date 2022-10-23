package com.nbstocks.nbstocks.presentation.ui.password_recovery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.databinding.FragmentPasswordRecoveryBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment

class PasswordRecoveryFragment : BaseFragment<FragmentPasswordRecoveryBinding>(FragmentPasswordRecoveryBinding::inflate) {
    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.btnSendRecoverEmail.setOnClickListener {  }
    }
}