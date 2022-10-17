package com.nbstocks.nbstocks.ui.home


import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.databinding.FragmentHomeBinding
import com.nbstocks.nbstocks.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun start() {
        listeners()
    }

    private fun listeners(){
        binding.btnBuy.setOnClickListener {
            //for testing
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStocksDetailsFragment())
        }
        binding.btnSell.setOnClickListener {  }
    }

}