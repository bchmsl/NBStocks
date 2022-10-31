package com.nbstocks.nbstocks.presentation.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.repositories.watchlist_stock.WatchlistStockRepositoryImpl
import com.nbstocks.nbstocks.presentation.mapper.toWatchListStockUiModel
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.model.resetViewState
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistStockRepositoryImpl: WatchlistStockRepositoryImpl
) : ViewModel() {

    private val _watchlistItemsState = MutableStateFlow<ViewState<List<String>>>(ViewState())
    val watchlistItemsState: StateFlow<ViewState<List<String>>> get() = _watchlistItemsState

    private val _watchlistStocksState =
        MutableStateFlow<ViewState<WatchlistStockInfoUiModel>>(ViewState())
    val watchlistStocksState: StateFlow<ViewState<WatchlistStockInfoUiModel>> get() = _watchlistStocksState

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getItemsFromWatchlist() {
        viewModelScope.launch {
            watchlistStockRepositoryImpl.getWatchlistItems()
            _watchlistItemsState.resetViewState()
            watchlistStockRepositoryImpl.stockState.collect {
                _loaderState.value = it.isLoading
                when (it) {
                    is Resource.Success -> {
//                        d("stocks_vm","${it.data}")
                        _watchlistItemsState.emit(_watchlistItemsState.value.copy(data = it.data))
                    }
                    is Resource.Error -> {
                        _watchlistItemsState.emit(_watchlistItemsState.value.copy(error = it.error))
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun getWatchlistStocksInformation(symbols: List<String>) {
        viewModelScope.launch {
            watchlistStockRepositoryImpl.getWatchlistStocksInformation(symbols.joinToString(","))
                .collect {
                    _watchlistStocksState.resetViewState()
                    when (it) {
                        is Resource.Success -> {
                            _watchlistStocksState.emit(_watchlistStocksState.value.copy(data = it.data.toWatchListStockUiModel()))
                        }
                        is Resource.Error -> {
                            _watchlistStocksState.emit(_watchlistStocksState.value.copy(error = it.error))
                        }
                        is Resource.Loading -> {}
                    }
                }
        }
    }
}