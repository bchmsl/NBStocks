package com.nbstocks.nbstocks.presentation.ui.company_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.repositories.company_listings.CompanyListingsRepository
import com.nbstocks.nbstocks.presentation.mapper.toCompanyListingUiModel
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.model.resetViewState
import com.nbstocks.nbstocks.presentation.ui.company_listings.model.CompanyListingUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(private val repository: CompanyListingsRepository) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<List<CompanyListingUiModel>>>(ViewState())
    val viewState: StateFlow<ViewState<List<CompanyListingUiModel>>> get() = _viewState

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getCompanyListings(fetchFromRemote: Boolean, query: String?) {
        viewModelScope.launch {
            _viewState.resetViewState()
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
}