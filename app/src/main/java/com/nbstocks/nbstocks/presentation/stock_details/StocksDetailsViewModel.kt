package com.nbstocks.nbstocks.presentation.stock_details

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.domain.repositories.stock_details.StockDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksDetailsViewModel  @Inject constructor(private val stockDetailsRepository: StockDetailsRepository) : ViewModel() {

    fun load(){
        viewModelScope.launch {
            stockDetailsRepository.getStocksDetails("AAPL")
            d("logii","vm")
        }
    }

}