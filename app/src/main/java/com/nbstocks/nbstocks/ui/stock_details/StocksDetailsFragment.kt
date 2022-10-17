package com.nbstocks.nbstocks.ui.stock_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.databinding.FragmentStocksDetailsBinding
import com.nbstocks.nbstocks.ui.base.BaseFragment

class StocksDetailsFragment :
    BaseFragment<FragmentStocksDetailsBinding>(FragmentStocksDetailsBinding::inflate) {
    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.btnDaily.setOnClickListener { }
        binding.btnMonthly.setOnClickListener { }

        binding.btnBuy.setOnClickListener {
            showConfirmation()
        }

        binding.btnSell.setOnClickListener {
            showConfirmation()
        }

    }

    private fun showConfirmation() {
        val animation = AnimationUtils.loadAnimation(
            requireContext(),
            androidx.transition.R.anim.abc_slide_in_top
        )
        binding.btnSell.visibility = View.INVISIBLE
        binding.btnBuy.visibility = View.INVISIBLE
        binding.vConfirmBuySell.visibility = View.VISIBLE
        binding.vConfirmBuySell.startAnimation(animation)
        binding.etCash.visibility = View.VISIBLE
        binding.etCash.startAnimation(animation)
        binding.tvResult.visibility = View.VISIBLE
        binding.tvResult.startAnimation(animation)
    }
}