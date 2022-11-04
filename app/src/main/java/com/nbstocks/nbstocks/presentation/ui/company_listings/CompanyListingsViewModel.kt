package com.nbstocks.nbstocks.presentation.ui.company_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.domain.repositories.company_listings.CompanyListingsRepository
import com.nbstocks.nbstocks.presentation.mapper.toCompanyListingUiModel
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.ui.company_listings.model.CompanyListingUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(private val repository: CompanyListingsRepository) : ViewModel() {

    private val _companyListingViewState = MutableStateFlow<ViewState<List<CompanyListingUiModel>>>(ViewState())
    val companyListingViewState: StateFlow<ViewState<List<CompanyListingUiModel>>> get() = _companyListingViewState

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getCompanyListings(fetchFromRemote: Boolean, query: String?, invokeOnCompletion: (() -> Unit)? = null) {
        viewModelScope.launch {
            _companyListingViewState.resetViewState()
            repository.getCompanyListings(fetchFromRemote, query).collect { resource ->
                _loaderState.value = resource.isLoading

                resource.doOnSuccess {
                    _companyListingViewState.emitSuccessViewState(this){
                        it.map { it.toCompanyListingUiModel() }
                    }
                }.doOnFailure {
                    _companyListingViewState.emitErrorViewState(this){ it }
                }
            }
            invokeOnCompletion?.invoke()
        }
    }
}