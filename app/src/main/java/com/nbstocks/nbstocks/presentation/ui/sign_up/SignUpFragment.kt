package com.nbstocks.nbstocks.presentation.ui.sign_up

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.common.extensions.makeSnackbar
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.databinding.FragmentSignUpBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
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
        binding.tvLogIn.setOnClickListener {
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
                        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
                    }
                    is Resource.Error -> {
                        binding.root.makeSnackbar("Register Failed", true)
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }
}


