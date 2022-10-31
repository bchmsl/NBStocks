package com.nbstocks.nbstocks.presentation.ui.profile

import com.nbstocks.nbstocks.databinding.FragmentProfileBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.apply {
            swBalanceVisible.setOnCheckedChangeListener { buttonView, isChecked ->

            }
            tvAbout.setOnClickListener {

            }
            tvChangeEmail.setOnClickListener {

            }
            tvChangePassword.setOnClickListener {

            }
            tvLogOut.setOnClickListener {

            }
        }
    }
}