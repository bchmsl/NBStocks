package com.nbstocks.nbstocks.presentation.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.local.datastore.DatastoreProvider.readPreference
import com.nbstocks.nbstocks.data.mapper.toUserStockUiModel
import com.nbstocks.nbstocks.data.repositories.db_owned_stocks.OwnedStocksRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.db_balance.BalanceRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.trade_history.TradeHistoryRepositoryImpl
import com.nbstocks.nbstocks.domain.model.TradeHistoryDomainModel
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.ui.home.model.TradeHistoryUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usersStockRepositoryImpl: OwnedStocksRepositoryImpl,
    private val balanceRepositoryImpl: BalanceRepositoryImpl,
    private val tradeHistoryRepositoryImpl: TradeHistoryRepositoryImpl
) : ViewModel() {

    private val _usersStockState = MutableStateFlow<ViewState<List<UsersStockUiModel>>>(ViewState())
    val usersStockState: StateFlow<ViewState<List<UsersStockUiModel>>> get() = _usersStockState

    private val _usersBalanceState = MutableStateFlow<ViewState<String>>(ViewState())
    val usersBalanceState: StateFlow<ViewState<String>> get() = _usersBalanceState

    private val _balanceShownState = MutableStateFlow<Boolean>(false)
    val balanceShownState: StateFlow<Boolean> get() = _balanceShownState


    private val _tradeHistoryState = MutableStateFlow<ViewState<List<TradeHistoryDomainModel>>>(ViewState())
    val tradeHistoryState: StateFlow<ViewState<List<TradeHistoryDomainModel>>> get() = _tradeHistoryState

    fun getUsersStocks() {
        viewModelScope.launch {
            usersStockRepositoryImpl.getOwnedStocks()
            _usersStockState.resetViewState()
            usersStockRepositoryImpl.ownedStockState.collect { resource ->
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

    fun getTradeHistory() {
        viewModelScope.launch {
            tradeHistoryRepositoryImpl.getTradeHistory()
            _tradeHistoryState.resetViewState()
            tradeHistoryRepositoryImpl.tradeHistoryState.collect { resource ->

                resource.doOnSuccess {
                    _tradeHistoryState.emitSuccessViewState(this){it}
                }.doOnFailure {
                    _tradeHistoryState.emitErrorViewState(this){it}
                }
            }
        }
    }

    fun setBalance(newBalance: Double) {
        viewModelScope.launch {
            balanceRepositoryImpl.changeBalance(newBalance)
        }
    }

    fun showBalance(
        context: Context
    ) {
        viewModelScope.launch {
            context.readPreference(true).let { _balanceShownState.emit(it) }
        }
    }
}