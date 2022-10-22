package com.nbstocks.nbstocks.presentation.stock_details

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.common.mapper.toDailyStockUiModelList
import com.nbstocks.nbstocks.domain.model.DailyStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.stock_details.StockDetailsRepository
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.model.resetViewState
import com.nbstocks.nbstocks.presentation.stock_details.model.DailyStockUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksDetailsViewModel  @Inject constructor(private val stockDetailsRepository: StockDetailsRepository) : ViewModel() {

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