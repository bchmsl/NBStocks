package com.nbstocks.nbstocks.presentation.profile

import com.nbstocks.nbstocks.MainActivity
import com.nbstocks.nbstocks.databinding.FragmentProfileBinding
import com.nbstocks.nbstocks.presentation.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideToolBar()
    }
}