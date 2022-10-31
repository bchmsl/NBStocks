package com.nbstocks.nbstocks.presentation.ui.stock_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toUserStockDomainModel
import com.nbstocks.nbstocks.data.repositories.db_add_users_stock.DbManageUsersStockRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockPricesRepository
import com.nbstocks.nbstocks.domain.repositories.db_add_users_stock.DbManageUsersStockRepository
import com.nbstocks.nbstocks.domain.repositories.watchlist_stock.WatchlistStockRepository
import com.nbstocks.nbstocks.presentation.mapper.toCurrentStockUiModel
import com.nbstocks.nbstocks.presentation.mapper.toIntervalStockPricesUiModel
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.model.resetViewState
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.IntervalStockPricesUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksDetailsViewModel @Inject constructor(
    private val stockDetailsRepository: DailyStockPricesRepository,
    private val currentStockRepository: CurrentStockRepository,
    private val watchlistStockRepository: WatchlistStockRepository,
    private val dbManageUsersStockRepository: DbManageUsersStockRepository,
    private val dbManageUsersStockRepositoryImpl: DbManageUsersStockRepositoryImpl
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<IntervalStockPricesUiModel>>(ViewState())
    val viewState: StateFlow<ViewState<IntervalStockPricesUiModel>> get() = _viewState

    private val _currentStockState = MutableStateFlow<ViewState<CurrentStockUiModel>>(ViewState())
    val currentStockState: StateFlow<ViewState<CurrentStockUiModel>> get() = _currentStockState


    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getStocksDetails(symbol: String, range: String, interval: String) {
        viewModelScope.launch {
            _viewState.resetViewState()
            stockDetailsRepository.getStocksDetails(symbol, range, interval).collect { it ->
                _loaderState.value = it.isLoading
                when (it) {
                    is Resource.Success -> _viewState.emit(
                        _viewState.value.copy(
                            data = it.data?.toIntervalStockPricesUiModel()
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
                        _currentStockState.emit(_currentStockState.value.copy(data = it.data.toCurrentStockUiModel(symbol)))
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

    fun addStockInWatchlist(symbol: String) {
        viewModelScope.launch {
            watchlistStockRepository.addWatchlistStock(symbol)
        }
    }

    fun removeStockInWatchlist(symbol: String) {
        viewModelScope.launch {
            watchlistStockRepository.removeWatchlistStock(symbol)
        }
    }

    fun buyStockToOwner(usersStockUiModel: UsersStockUiModel) {
        viewModelScope.launch {
            dbManageUsersStockRepositoryImpl.buyUsersStock(
                usersStockDomainModel = usersStockUiModel.toUserStockDomainModel()
            )
        }
    }

//    fun sellStockFromOwner(model){
//        viewModelScope.launch {
//            dbManageUsersStockRepository.addUsersStock(model)
//        }
//    }
}