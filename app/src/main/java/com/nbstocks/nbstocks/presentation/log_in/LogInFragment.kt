package com.nbstocks.nbstocks.presentation.log_in

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.MainActivity
import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.common.extensions.snackbar.makeSnackbar
import com.nbstocks.nbstocks.databinding.FragmentLogInBinding
import com.nbstocks.nbstocks.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun start() {
        listeners()
        observer()

    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
                        }
                        is Resource.Error -> {
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            }
        }
    }

    private fun logIn() {

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.signIn(email, password)
    }


    private fun listeners() {
        binding.btnSignIn.setOnClickListener {
            logIn()
        }
        binding.tvSignUpHere.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToPasswordRecoveryFragment())
        }
    }
}