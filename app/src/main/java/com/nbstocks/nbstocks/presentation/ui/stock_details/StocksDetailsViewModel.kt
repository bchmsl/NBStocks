package com.nbstocks.nbstocks.presentation.ui.stock_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toCurrentStockUiModel
import com.nbstocks.nbstocks.domain.repositories.watchlist_stock.WatchlistStockRepository
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockPricesRepository
import com.nbstocks.nbstocks.presentation.mapper.toCurrentStockDomain
import com.nbstocks.nbstocks.presentation.mapper.toStockPricesModelList
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.model.resetViewState
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.StockPricesUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksDetailsViewModel @Inject constructor(
    private val stockDetailsRepository: DailyStockPricesRepository,
    private val currentStockRepository: CurrentStockRepository,
    private val watchlistStockRepository: WatchlistStockRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<List<StockPricesUiModel>>>(ViewState())
    val viewState: StateFlow<ViewState<List<StockPricesUiModel>>> get() = _viewState

    private val _currentStockState = MutableStateFlow<ViewState<CurrentStockUiModel>>(ViewState())
    val currentStockState: StateFlow<ViewState<CurrentStockUiModel>> get() = _currentStockState


    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getStocksDetails(symbol: String, function: String) {
        viewModelScope.launch {
            _viewState.resetViewState()
            stockDetailsRepository.getStocksDetails(symbol, function).collect { it ->
                _loaderState.value = it.isLoading
                when (it) {
                    is Resource.Success -> _viewState.emit(
                        _viewState.value.copy(
                            data = it.data.toStockPricesModelList()
                        )
                    )
                    is Resource.Error -> _viewState.emit(
                        _viewState.value.copy(
                            error = it.error
                        )
                    )
                    else -> {}
                }
            }
        }
    }

    fun getCurrentStock(symbol: String) {
        viewModelScope.launch {
            _currentStockState.resetViewState()
            currentStockRepository.getCurrentStock(symbol).collect {
                _loaderState.value = it.isLoading
                when (it) {
                    is Resource.Success -> {
                        _currentStockState.emit(_currentStockState.value.copy(data = it.data.toCurrentStockUiModel()))
                    }
                    is Resource.Error -> {
                        _currentStockState.emit(
                            _currentStockState.value.copy(
                                error = it.error
                            )
                        )
                    }
                    else -> {}
                }

            }
        }
    }

    fun addStockInWatchlist(currentStockUiModel: CurrentStockUiModel) {
        viewModelScope.launch {
            watchlistStockRepository.addWatchlistStock(currentStockUiModel.toCurrentStockDomain())
        }
    }

    fun removeStockInWatchlist(currentStockUiModel: CurrentStockUiModel){
        viewModelScope.launch {
            watchlistStockRepository.removeWatchlistStock(currentStockUiModel.toCurrentStockDomain())
        }
    }

}