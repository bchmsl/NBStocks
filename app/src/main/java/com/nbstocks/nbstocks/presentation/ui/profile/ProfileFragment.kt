package com.nbstocks.nbstocks.presentation.ui.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.common.extensions.makeSnackbar
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.databinding.FragmentProfileBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.apply {
            swBalanceVisible.setOnCheckedChangeListener { buttonView, isChecked ->

            }
            tvAbout.setOnClickListener {

            }

            tvChangePassword.setOnClickListener {

            }
            tvLogOut.setOnClickListener {
                signOut()
            }
        }
    }

//    private fun savePassword(password: String) {
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.changePassword(password)
//            viewModel.changePasswordResponse.collect {
//                when (it) {
//                    is Resource.Success -> {
//                        binding.root.makeSnackbar("Password changed successfully", false)
//                    }
//                    is Resource.Error -> {
//                        binding.root.makeSnackbar("Password change failed", true)
//                    }
//                    else -> {}
//                }
//            }
//        }
//    }

    private fun signOut() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLogInFragment())
        FirebaseAuth.getInstance().signOut()
    }
}