package com.nbstocks.nbstocks.presentation.ui.watchlist_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.data.repositories.watchlist_stock.WatchlistStockRepositoryImpl
import com.nbstocks.nbstocks.presentation.mapper.toCurrentStockUiModel
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.model.resetViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistStockRepositoryImpl: WatchlistStockRepositoryImpl
) : ViewModel() {

    private val _watchlistStockState = MutableStateFlow<ViewState<List<String>>>(ViewState())
    val watchlistStockState: StateFlow<ViewState<List<String>>> get() = _watchlistStockState

    fun getStockFromWatchlist() {
        viewModelScope.launch {
            watchlistStockRepositoryImpl.getWatchlistStock()
            _watchlistStockState.resetViewState()
            watchlistStockRepositoryImpl.stockState.collect {
                when (it) {
                    is Resource.Success -> {
//                        d("stocks_vm","${it.data}")
                        _watchlistStockState.emit(_watchlistStockState.value.copy(data = it.data))
                    }
                    is Resource.Error -> {
                        _watchlistStockState.emit(_watchlistStockState.value.copy(error = it.error))
                    }
                    is Resource.Loading -> TODO()
                }
            }
        }
    }

}