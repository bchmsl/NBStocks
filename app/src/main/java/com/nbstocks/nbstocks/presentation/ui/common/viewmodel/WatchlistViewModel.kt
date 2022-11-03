package com.nbstocks.nbstocks.presentation.ui.common.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.data.repositories.watchlist_stock.WatchlistStockRepositoryImpl
import com.nbstocks.nbstocks.presentation.mapper.toWatchListStockUiModel
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _ownedStocksState =
        MutableStateFlow<ViewState<WatchlistStockInfoUiModel>>(ViewState())
    val ownedStocksState: StateFlow<ViewState<WatchlistStockInfoUiModel>> get() = _ownedStocksState

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getItemsFromWatchlist() {
        viewModelScope.launch {
            watchlistStockRepositoryImpl.getHomeScreenItems()
            _watchlistItemsState.resetViewState()
            watchlistStockRepositoryImpl.stockState.collect { resource ->
                _loaderState.value = resource.isLoading
                resource.doOnSuccess {
                    _watchlistItemsState.emitSuccessViewState(this) { it }
                }.doOnFailure {
                    _watchlistItemsState.emitErrorViewState(this) { it }
                }
            }
        }
    }

    fun getWatchlistStocksInformation(symbols: List<String>, isWatchList: Boolean) {
        viewModelScope.launch {
            _watchlistStocksState.resetViewState()
            watchlistStockRepositoryImpl.getWatchlistStocksInformation(symbols.joinToString(","))
                .collect { resource ->
                    _loaderState.value = resource.isLoading
                    if (isWatchList){
                        resource.doOnSuccess {
                            _watchlistStocksState.emitSuccessViewState(this) {
                                it.toWatchListStockUiModel()
                            }
                        }.doOnFailure {
                            _watchlistStocksState.emitErrorViewState(this) { it }
                        }
                    }else{
                        resource.doOnSuccess {
                            _ownedStocksState.emitSuccessViewState(this) {
                                it.toWatchListStockUiModel()
                            }
                        }.doOnFailure {
                            _ownedStocksState.emitErrorViewState(this) { it }
                        }
                    }
                }
        }
    }
}