package com.nbstocks.nbstocks.presentation.stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.common.mapper.toCompanyListingUiModel
import com.nbstocks.nbstocks.domain.repositories.StockRepository
import com.nbstocks.nbstocks.presentation.model.CompanyListingUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(private val repository: StockRepository) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<List<CompanyListingUiModel>>>(ViewState())
    val viewState: StateFlow<ViewState<List<CompanyListingUiModel>>> get() = _viewState

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getCompanyListings(fetchFromRemote: Boolean, query: String?) {
        viewModelScope.launch {
            resetViewState()
            repository.getCompanyListings(fetchFromRemote, query).collect { it ->
                _loaderState.value = it.isLoading
                when (it) {
                    is Resource.Success -> _viewState.emit(
                        _viewState.value.copy(
                            data = it.data.map { it.toCompanyListingUiModel() }
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

    private fun resetViewState() {
        _viewState.value = _viewState.value.copy(data = null, error = null)
    }

    data class ViewState<T>(
        val data: T? = null,
        val error: Throwable? = null
    )
}