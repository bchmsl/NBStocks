package com.nbstocks.nbstocks.presentation.ui.password_recovery

import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.databinding.FragmentPasswordRecoveryBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment

class PasswordRecoveryFragment : BaseFragment<FragmentPasswordRecoveryBinding>(FragmentPasswordRecoveryBinding::inflate) {
    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.btnSendRecoverEmail.setOnClickListener {

        }
        binding.vArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}