package com.nbstocks.nbstocks.presentation.log_in

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.databinding.FragmentLogInBinding
import com.nbstocks.nbstocks.presentation.base.BaseFragment

class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {
    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
        }
        binding.tvSignUpHere.setOnClickListener{
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToPasswordRecoveryFragment())
        }
    }
}