package com.nbstocks.nbstocks.presentation.ui.user_stock_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.data.mapper.toUserStockUiModel
import com.nbstocks.nbstocks.data.repositories.db_owned_stocks.OwnedStocksRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.multiple_stocks.MultipleStocksRepository
import com.nbstocks.nbstocks.presentation.mapper.toWatchListStockUiModel
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserStockListingViewModel @Inject constructor(
    private val usersStockRepositoryImpl: OwnedStocksRepositoryImpl,
    private val multipleStocksRepository: MultipleStocksRepository
) : ViewModel() {

    private val _usersStockState = MutableStateFlow<ViewState<List<UsersStockUiModel>>>(ViewState())
    val usersStockState: StateFlow<ViewState<List<UsersStockUiModel>>> get() = _usersStockState

    private val _ownedStocksInfoState =
        MutableStateFlow<ViewState<WatchlistStockInfoUiModel>>(ViewState())
    val ownedStocksInfoState: StateFlow<ViewState<WatchlistStockInfoUiModel>> get() = _ownedStocksInfoState

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getUsersStocks() {
        viewModelScope.launch {
            usersStockRepositoryImpl.getOwnedStocks()
            _usersStockState.resetViewState()
            usersStockRepositoryImpl.ownedStockState.collect { resource ->
                _loaderState.emit(resource.isLoading)
                resource.doOnSuccess {
                    _usersStockState.emitSuccessViewState(this) {
                        it.map { it.toUserStockUiModel() }
                    }
                }.doOnFailure {
                    _usersStockState.emitErrorViewState(this) { it }
                }
            }
        }
    }


    fun getUserStocksInformation(symbols: List<String>) {
        viewModelScope.launch {
            multipleStocksRepository.getWatchlistStocksInformation(symbols.joinToString(","))
                .collect { resource ->
                    _loaderState.emit(resource.isLoading)
                    _ownedStocksInfoState.resetViewState()
                    resource.doOnSuccess {
                        _ownedStocksInfoState.emitSuccessViewState(this) {
                            it.toWatchListStockUiModel()
                        }
                    }.doOnFailure {
                        _ownedStocksInfoState.emitErrorViewState(this) { it }
                    }
                }
        }
    }

}