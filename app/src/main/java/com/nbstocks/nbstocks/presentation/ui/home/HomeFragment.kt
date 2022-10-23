package com.nbstocks.nbstocks.presentation.ui.home


import androidx.navigation.fragment.findNavController
import com.nbstocks.nbstocks.presentation.ui.MainActivity
import com.nbstocks.nbstocks.databinding.FragmentHomeBinding
import com.nbstocks.nbstocks.presentation.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun start() {
        listeners()

        val activity = requireActivity() as? MainActivity
        activity?.showToolBar()

        activity?.binding?.fabStocks?.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStocksFragment())
        }

    }

    private fun listeners(){
        binding.btnBuy.setOnClickListener {
            //for testing
//            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStocksDetailsFragment())
        }
        binding.btnSell.setOnClickListener {

        }
    }

}