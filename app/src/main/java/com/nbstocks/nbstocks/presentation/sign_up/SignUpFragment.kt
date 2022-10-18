package com.nbstocks.nbstocks.presentation.sign_up

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.databinding.FragmentSignUpBinding
import com.nbstocks.nbstocks.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun start() {
        listeners()
        observer()
    }

    private fun listeners() {
        binding.btnSignUp.setOnClickListener {
            registration()
        }
        binding.vArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun registration() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.signUp(email, password)
    }

    private fun observer() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.registerResponse.collect {
                when (it) {
                    is Resource.Success -> {
                        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment())
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


