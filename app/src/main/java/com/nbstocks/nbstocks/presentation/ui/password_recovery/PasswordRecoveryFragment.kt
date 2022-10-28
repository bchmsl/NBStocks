package com.nbstocks.nbstocks.presentation.ui.password_recovery

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nbstocks.nbstocks.databinding.FragmentPasswordRecoveryBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment

class PasswordRecoveryFragment : BaseFragment<FragmentPasswordRecoveryBinding>(FragmentPasswordRecoveryBinding::inflate) {
    private val args: PasswordRecoveryFragmentArgs by navArgs()
    override fun start() {
        binding.etEmail.setText(args.email)
        listeners()
    }

    private fun listeners() {
        binding.btnSendRecoverEmail.setOnClickListener {

        }
        binding.ibtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvSignIn.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}