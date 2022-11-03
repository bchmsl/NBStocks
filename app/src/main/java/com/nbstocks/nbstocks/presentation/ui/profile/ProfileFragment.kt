package com.nbstocks.nbstocks.presentation.ui.profile

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.common.extensions.asynchronously
import com.nbstocks.nbstocks.common.extensions.obtainViewModel
import com.nbstocks.nbstocks.databinding.FragmentProfileBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by lazy {
        obtainViewModel(
            requireActivity(),
            ProfileViewModel::class.java,
            defaultViewModelProviderFactory
        )
    }
    override fun start() {
        observe()
        listeners()
    }

    private fun observe() {
        asynchronously {
            viewModel.getShownBalanceState(requireContext()).collect{
                binding.swBalanceVisible.isChecked = it
                Log.wtf("TAGGGG", it.toString())
            }
        }
    }

    private fun listeners() {
        binding.apply {
            swBalanceVisible.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.setShownBalance(requireContext(),isChecked)
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