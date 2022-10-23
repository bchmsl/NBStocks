package com.nbstocks.nbstocks.presentation.ui.stock_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockRepository
import com.nbstocks.nbstocks.presentation.mapper.toDailyStockUiModelList
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.model.resetViewState
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.DailyStockUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksDetailsViewModel  @Inject constructor(private val stockDetailsRepository: DailyStockRepository) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<List<DailyStockUiModel>>>(ViewState())
    val viewState: StateFlow<ViewState<List<DailyStockUiModel>>> get() = _viewState

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

}