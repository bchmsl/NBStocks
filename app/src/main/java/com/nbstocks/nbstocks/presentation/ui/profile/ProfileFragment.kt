package com.nbstocks.nbstocks.presentation.ui.profile

import com.nbstocks.nbstocks.presentation.ui.MainActivity
import com.nbstocks.nbstocks.databinding.FragmentProfileBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideToolBar()
    }
}