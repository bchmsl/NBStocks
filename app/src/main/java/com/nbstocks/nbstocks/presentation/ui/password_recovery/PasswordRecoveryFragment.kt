package com.nbstocks.nbstocks.presentation.ui.password_recovery

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nbstocks.nbstocks.common.extensions.makeSnackbar
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.databinding.FragmentPasswordRecoveryBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordRecoveryFragment : BaseFragment<FragmentPasswordRecoveryBinding>(FragmentPasswordRecoveryBinding::inflate) {

    private val viewModel: PasswordRecoveryViewModel by viewModels()

    private val args: PasswordRecoveryFragmentArgs by navArgs()
    override fun start() {
        binding.etEmail.setText(args.email)
        listeners()
    }

    private fun listeners() {
        binding.btnSendRecoverEmail.setOnClickListener {
            resetPassword()
        }
        binding.ibtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvSignIn.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun resetPassword() {
        with(binding) {
            val email = etEmail.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.resetPassword(email)
                viewModel.resetPasswordResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            findNavController().navigate(PasswordRecoveryFragmentDirections.actionPasswordRecoveryFragmentToLogInFragment())
                        }
                        is Resource.Error -> {
                            binding.root.makeSnackbar(it.error.toString(),true)
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            }

        }
    }

}