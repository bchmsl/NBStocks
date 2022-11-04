package com.nbstocks.nbstocks.presentation.ui.log_in

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.databinding.FragmentLogInBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun start() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
        }
        listeners()
        observer()
    }

    private fun observer() {
        asynchronously {
            viewModel.loginResponse.collect {resource ->
                resource.doOnSuccess {
                    findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
                }.doOnFailure {
                    it.localizedMessage?.let { it1 -> binding.root.makeSnackbar(it1, true) }
                }
            }
        }
        asynchronously {
            viewModel.loaderState.collect{
                binding.progressBar.isVisible = it
            }
        }
    }


    private fun logIn() {
        binding.tilEmail.isValid(isEmail = true)?.let { email ->
            binding.tilPassword.isValid(isPassword = true)?.let { password ->
                viewModel.signIn(email, password)
            }
        }
    }


    private fun listeners() {
        binding.btnSignIn.setOnClickListener {
            logIn()
        }
        binding.tvSignUpHere.setOnClickListener {
            findNavController().navigate(
                LogInFragmentDirections.actionLogInFragmentToSignUpFragment(
                    binding.etEmail.text.toString()
                )
            )
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(
                LogInFragmentDirections.actionLogInFragmentToPasswordRecoveryFragment(
                    binding.etEmail.text.toString()
                )
            )
        }
    }
}