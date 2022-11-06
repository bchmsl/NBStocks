package com.nbstocks.nbstocks.presentation.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.data.repositories.watchlist_stock.WatchlistRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.multiple_stocks.MultipleStocksRepository
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
    private val watchlistRepositoryImpl: WatchlistRepositoryImpl,
    private val multipleStocksRepository: MultipleStocksRepository
) : ViewModel() {

    private val _watchlistItemsState = MutableStateFlow<ViewState<List<String>>>(ViewState())
    val watchlistItemsState: StateFlow<ViewState<List<String>>> get() = _watchlistItemsState

    private val _usersStocksState =
        MutableStateFlow<ViewState<WatchlistStockInfoUiModel>>(ViewState())
    val watchlistStocksState: StateFlow<ViewState<WatchlistStockInfoUiModel>> get() = _usersStocksState

    private val _ownedStocksState =
        MutableStateFlow<ViewState<WatchlistStockInfoUiModel>>(ViewState())
    val ownedStocksState: StateFlow<ViewState<WatchlistStockInfoUiModel>> get() = _ownedStocksState

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getItemsFromWatchlist() {
        viewModelScope.launch {
            watchlistRepositoryImpl.getWatchlistItems()
            _watchlistItemsState.resetViewState()
            watchlistRepositoryImpl.watchlistItems.collect { resource ->
                _loaderState.value = resource.isLoading
                resource.doOnSuccess {
                    _watchlistItemsState.emitSuccessViewState(this) { it }
                }.doOnFailure {
                    _watchlistItemsState.emitErrorViewState(this) { it }
                }
            }
        }
    }

    fun getUserStocksInformation(symbols: List<String>, isWatchList: Boolean) {
        viewModelScope.launch {
            _usersStocksState.resetViewState()
            multipleStocksRepository.getWatchlistStocksInformation(symbols.joinToString(","))
                .collect { resource ->
                    _loaderState.value = resource.isLoading
                    if (isWatchList){
                        resource.doOnSuccess {
                            _usersStocksState.emitSuccessViewState(this) {
                                it.toWatchListStockUiModel()
                            }
                        }.doOnFailure {
                            _usersStocksState.emitErrorViewState(this) { it }
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