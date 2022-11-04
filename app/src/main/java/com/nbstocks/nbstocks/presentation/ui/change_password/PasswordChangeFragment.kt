package com.nbstocks.nbstocks.presentation.ui.change_password

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.common.extensions.asynchronously
import com.nbstocks.nbstocks.common.extensions.makeSnackbar
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.databinding.FragmentPasswordChangeBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PasswordChangeFragment :
    BaseFragment<FragmentPasswordChangeBinding>(FragmentPasswordChangeBinding::inflate) {

    private val viewModel: PasswordChangeViewModel by viewModels()

    override fun start() {
        listeners()
    }

    private fun listeners() {

        binding.btnChangePassword.setOnClickListener {
            changePassword()
        }

        binding.ibtnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    private fun changePassword() {

        val newPassword = binding.etChangePassword.text.toString()
        val confirmNewPassword = binding.etChangePasswordConfirm.text.toString()

        asynchronously {
            viewModel.changePassword(newPassword, confirmNewPassword)
            viewModel.changePasswordResponse.collect {
                when (it) {

                    is Resource.Success -> {
                        binding.root.makeSnackbar("Password changed", false)
                    }
                    else -> {
                        binding.root.makeSnackbar("$it", false)
                    }
                }
            }
        }
    }
}