package com.nbstocks.nbstocks.presentation.ui.stock_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.data.mapper.toUserStockDomainModel
import com.nbstocks.nbstocks.data.repositories.db_owned_stocks.OwnedStocksRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.db_balance.BalanceRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.db_get_stock_amount.GetStockAmountRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.watchlist_stock.WatchlistRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockPricesRepository
import com.nbstocks.nbstocks.domain.repositories.trade_history.TradeHistoryRepository
import com.nbstocks.nbstocks.presentation.mapper.toCurrentStockUiModel
import com.nbstocks.nbstocks.presentation.mapper.toIntervalStockPricesUiModel
import com.nbstocks.nbstocks.presentation.mapper.toTradeHistoryDomainModel
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.ui.home.model.TradeHistoryUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.IntervalStockPricesUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksDetailsViewModel @Inject constructor(
    private val stockDetailsRepository: DailyStockPricesRepository,
    private val currentStockRepository: CurrentStockRepository,
    private val watchlistStockRepositoryImpl: WatchlistRepositoryImpl,
    private val ownedStocksRepositoryImpl: OwnedStocksRepositoryImpl,
    private val getStockAmountRepositoryImpl: GetStockAmountRepositoryImpl,
    private val balanceRepositoryImpl: BalanceRepositoryImpl,
    private val tradeHistoryRepository: TradeHistoryRepository
) : ViewModel() {

    private val _intervalStockPricesViewState =
        MutableStateFlow<ViewState<IntervalStockPricesUiModel>>(ViewState())
    val intervalStockPricesViewState: StateFlow<ViewState<IntervalStockPricesUiModel>> get() = _intervalStockPricesViewState

    private val _currentStockViewState =
        MutableStateFlow<ViewState<CurrentStockUiModel>>(ViewState())
    val currentStockViewState: StateFlow<ViewState<CurrentStockUiModel>> get() = _currentStockViewState

    private val _usersBalanceState = MutableStateFlow<ViewState<String>>(ViewState())
    val usersBalanceState: StateFlow<ViewState<String>> get() = _usersBalanceState

    private val _amountOfStock = MutableStateFlow<ViewState<String>>(ViewState())
    val amountOfStock = _amountOfStock.asStateFlow()

    private val _watchlistItems = MutableStateFlow<ViewState<List<String>>>(ViewState())
    val watchlistItems: StateFlow<ViewState<List<String>>> get() = _watchlistItems

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun getStocksDetails(symbol: String, range: String, interval: String) {
        viewModelScope.launch {

            _intervalStockPricesViewState.resetViewState()

            stockDetailsRepository.getStocksDetails(symbol, range, interval).collect { resource ->
                _loaderState.value = resource.isLoading

                resource.doOnSuccess {
                    if (it != null) {
                        _intervalStockPricesViewState.emitSuccessViewState(this) {
                            it.toIntervalStockPricesUiModel()
                        }
                    }
                }.doOnFailure {
                    _intervalStockPricesViewState.emitErrorViewState(this) { it }
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
            watchlistStockRepositoryImpl.addWatchlistStock(symbol)
        }
    }

    fun removeStockInWatchlist(symbol: String) {
        viewModelScope.launch {
            watchlistStockRepositoryImpl.removeWatchlistStock(symbol)
        }
    }

    fun getWatchlistItems() {
        viewModelScope.launch {
            watchlistStockRepositoryImpl.getWatchlistItems()
            _watchlistItems.resetViewState()
            watchlistStockRepositoryImpl.watchlistItems.collect { resource ->
                _loaderState.value = resource.isLoading
                resource.doOnSuccess {
                    _watchlistItems.emitSuccessViewState(this) {
                        Log.wtf(
                            "TTAAGG", it.toString()
                        )
                        it.toList()
                    }
                }.doOnFailure {
                    _watchlistItems.emitErrorViewState(this) { it }
                }

            }
        }
    }

    fun getStockAmount(symbol: String) {
        viewModelScope.launch {
            _amountOfStock.resetViewState()
            getStockAmountRepositoryImpl.getStockAmount(symbol)
            getStockAmountRepositoryImpl.stockAmount.collect { resource ->
                _loaderState.value = resource.isLoading
                resource.doOnSuccess {
                    _amountOfStock.emitSuccessViewState(this) { it }
                }.doOnFailure {
                    _amountOfStock.emitErrorViewState(this) { it }
                }
            }
        }
    }

    fun tradeStockToOwner(usersStockUiModel: UsersStockUiModel) {
        viewModelScope.launch {
            ownedStocksRepositoryImpl.buyStocks(
                usersStockDomainModel = usersStockUiModel.toUserStockDomainModel()
            )
        }
    }

    fun getBalance() {
        viewModelScope.launch {
            balanceRepositoryImpl.getBalance()
            _usersBalanceState.resetViewState()
            balanceRepositoryImpl.balance.collect { resource ->

                resource.doOnSuccess {
                    _usersBalanceState.emitSuccessViewState(this) { it }
                }.doOnFailure {
                    _usersBalanceState.emitErrorViewState(this) { it }
                }
            }

        }
    }

    fun removeUsersStock(symbol: String) {
        viewModelScope.launch {
            ownedStocksRepositoryImpl.sellOwnedStocks(symbol)
        }
    }

    fun changeBalance(money: Double) {
        viewModelScope.launch {
            balanceRepositoryImpl.changeBalance(money)
        }
    }

    fun addTradeHistory(tradeHistoryUiModel: TradeHistoryUiModel){
        viewModelScope.launch {
            tradeHistoryRepository.addTradeHistoryItem(tradeHistoryUiModel.toTradeHistoryDomainModel())
        }
    }

}