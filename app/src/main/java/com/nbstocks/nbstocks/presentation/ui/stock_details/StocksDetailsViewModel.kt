package com.nbstocks.nbstocks.presentation.ui.stock_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anychart.core.stock.series.Stick
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toCurrentStockUiModel
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockRepository
import com.nbstocks.nbstocks.presentation.mapper.toDailyStockUiModelList
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.model.resetViewState
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.DailyStockUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksDetailsViewModel @Inject constructor(
    private val stockDetailsRepository: DailyStockRepository,
    private val currentStockRepository: CurrentStockRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<List<DailyStockUiModel>>>(ViewState())
    val viewState: StateFlow<ViewState<List<DailyStockUiModel>>> get() = _viewState

    private val _currentStockState = MutableStateFlow<ViewState<CurrentStockUiModel>>(ViewState())
    val currentStockState: StateFlow<ViewState<CurrentStockUiModel>> get() = _currentStockState

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getStocksDetails(symbol: String) {
        viewModelScope.launch {
            _viewState.resetViewState()
            stockDetailsRepository.getStocksDetails(symbol).collect { it ->
                _loaderState.value = it.isLoading
                when (it) {
                    is Resource.Success -> _viewState.emit(
                        _viewState.value.copy(
                            data = it.data.toDailyStockUiModelList()
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
                        _currentStockState.value.copy(data = it.data.toCurrentStockUiModel())
                    }
                    is Resource.Error -> {
                        _currentStockState.value.copy(
                            error = it.error
                        )
                    }
                    else -> {}
                }

            }
        }
    }

}