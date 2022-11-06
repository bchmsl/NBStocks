package com.nbstocks.nbstocks.presentation.ui.password_recovery

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.databinding.FragmentPasswordRecoveryBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordRecoveryFragment :
    BaseFragment<FragmentPasswordRecoveryBinding>(FragmentPasswordRecoveryBinding::inflate) {

    private val viewModel: PasswordRecoveryViewModel by viewModels()
    private val args: PasswordRecoveryFragmentArgs by navArgs()

    override fun start() {
        binding.etEmail.setText(args.email)
        listeners()
        observe()
    }

    private fun observe() {
        asynchronously {
            viewModel.loaderState.collect {
                binding.progressBar.isVisible = it
            }
        }
    }

    private fun listeners() {
        binding.btnSendRecoverEmail.setOnClickListener {
            binding.tilEmail.isValid(isEmail = true)?.let {
                resetPassword(it)
            }
        }
        binding.ibtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun resetPassword(email: String) {
        asynchronously {
            viewModel.resetPassword(email)
            viewModel.resetPasswordResponse.collect { resource ->
                resource.doOnSuccess {
                    findNavController().popBackStack(R.id.logInFragment, false)

                }.doOnFailure {
                    binding.root.makeSnackbar(it.message.toString(), true)

                }
            }
        }
    }

}