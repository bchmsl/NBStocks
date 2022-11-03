package com.nbstocks.nbstocks.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.extensions.*
import com.nbstocks.nbstocks.data.mapper.toUserStockUiModel
import com.nbstocks.nbstocks.data.repositories.db_manage_users_stock.DbManageUsersStockRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.db_get_balance.GetBalanceRepositoryImpl
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usersStockRepositoryImpl: DbManageUsersStockRepositoryImpl,
    private val getBalanceRepositoryImpl: GetBalanceRepositoryImpl
) : ViewModel() {

    private val _usersStockState = MutableStateFlow<ViewState<List<UsersStockUiModel>>>(ViewState())
    val usersStockState: StateFlow<ViewState<List<UsersStockUiModel>>> get() = _usersStockState

    private val _usersBalanceState = MutableStateFlow<ViewState<String>>(ViewState())
    val usersBalanceState: StateFlow<ViewState<String>> get() = _usersBalanceState

    fun getUsersStocks() {
        viewModelScope.launch {
            usersStockRepositoryImpl.getUsersStock()
            _usersStockState.resetViewState()
            usersStockRepositoryImpl.stockState.collect { resource ->
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
            getBalanceRepositoryImpl.getBalance()
            _usersBalanceState.resetViewState()
            getBalanceRepositoryImpl.balance.collect { resource ->
                resource.doOnSuccess {
                    _usersBalanceState.emitSuccessViewState(this) { it }
                }.doOnFailure {
                    _usersBalanceState.emitErrorViewState(this) { it }
                }
            }

        }
    }
}
