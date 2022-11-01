package com.nbstocks.nbstocks.presentation.ui.stock_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.data.mapper.toUserStockDomainModel
import com.nbstocks.nbstocks.data.repositories.db_add_users_stock.DbManageUsersStockRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockPricesRepository
import com.nbstocks.nbstocks.domain.repositories.db_add_users_stock.DbManageUsersStockRepository
import com.nbstocks.nbstocks.domain.repositories.watchlist_stock.WatchlistStockRepository
import com.nbstocks.nbstocks.presentation.mapper.toCurrentStockUiModel
import com.nbstocks.nbstocks.presentation.mapper.toIntervalStockPricesUiModel
import com.nbstocks.nbstocks.presentation.model.ViewState
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

    private val _intervalStockpricesViewState =
        MutableStateFlow<ViewState<IntervalStockPricesUiModel>>(ViewState())
    val intervalStockPricesViewState: StateFlow<ViewState<IntervalStockPricesUiModel>> get() = _intervalStockpricesViewState

    private val _currentStockViewState =
        MutableStateFlow<ViewState<CurrentStockUiModel>>(ViewState())
    val currentStockViewState: StateFlow<ViewState<CurrentStockUiModel>> get() = _currentStockViewState

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getStocksDetails(symbol: String, range: String, interval: String) {
        viewModelScope.launch {

            _intervalStockpricesViewState.resetViewState()

            stockDetailsRepository.getStocksDetails(symbol, range, interval).collect { resource ->
                _loaderState.value = resource.isLoading

                resource.doOnSuccess {
                    if (it != null) {
                        _intervalStockpricesViewState.emitSuccessViewState(this) {
                            it.toIntervalStockPricesUiModel()
                        }
                    }
                }.doOnFailure {
                    _intervalStockpricesViewState.emitErrorViewState(this) { it }
                }

            }
        }
    }

    fun getCurrentStock(symbol: String) {
        viewModelScope.launch {
            _currentStockViewState.resetViewState()
            currentStockRepository.getCurrentStock(symbol).collect { resource ->
                _loaderState.value = resource.isLoading

                resource.doOnSuccess {
                    _currentStockViewState.emitSuccessViewState(this) {
                        it.toCurrentStockUiModel(symbol)
                    }
                }.doOnFailure {
                    _currentStockViewState.emitErrorViewState(this) { it }
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