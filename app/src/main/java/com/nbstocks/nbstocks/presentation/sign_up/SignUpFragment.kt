package com.nbstocks.nbstocks.presentation.sign_up

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.databinding.FragmentSignUpBinding
import com.nbstocks.nbstocks.presentation.base.BaseFragment

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment())
        }
        binding.vArrowBack.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment())
        }
    }
}