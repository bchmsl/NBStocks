package com.nbstocks.nbstocks.presentation.ui.change_password

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.databinding.FragmentPasswordChangeBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordChangeFragment :
    BaseFragment<FragmentPasswordChangeBinding>(FragmentPasswordChangeBinding::inflate) {

    private val viewModel: PasswordChangeViewModel by viewModels()

    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.btnChangePassword.setOnClickListener {
            binding.tilChangePassword.isValid(isPassword = true)?.let { password ->
                binding.tilChangePasswordConfirm.isValid(isPassword = true)
                    ?.let { confirmPassword ->
                        changePassword(password, confirmPassword)
                    }
            }
        }

        binding.ibtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun changePassword(newPassword: String, confirmNewPassword: String) {
        asynchronously {
            viewModel.changePassword(newPassword, confirmNewPassword)
            viewModel.changePasswordResponse.collect { resource ->
                resource.doOnSuccess {
                    binding.root.makeSnackbar("Password changed", false)
                    findNavController().popBackStack()
                }.doOnFailure {
                    findNavController().popBackStack()
                }
            }
        }
    }
}