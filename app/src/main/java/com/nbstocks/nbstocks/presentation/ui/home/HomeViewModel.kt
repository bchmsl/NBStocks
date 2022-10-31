package com.nbstocks.nbstocks.presentation.ui.home

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toCurrentStockUiModel
import com.nbstocks.nbstocks.data.mapper.toUserStockUiModel
import com.nbstocks.nbstocks.data.repositories.db_add_users_stock.DbManageUsersStockRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.watchlist_stock.WatchlistStockRepositoryImpl
import com.nbstocks.nbstocks.presentation.model.ViewState
import com.nbstocks.nbstocks.presentation.model.resetViewState
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val watchlistStockRepositoryImpl: WatchlistStockRepositoryImpl,
    private val usersStockRepositoryImpl: DbManageUsersStockRepositoryImpl
): ViewModel() {

    private val _watchlistStockState = MutableStateFlow<ViewState<List<CurrentStockUiModel>>>(ViewState())
    val watchlistStockState: StateFlow<ViewState<List<CurrentStockUiModel>>> get() = _watchlistStockState

    private val _usersStockState = MutableStateFlow<ViewState<List<UsersStockUiModel>>>(ViewState())
    val usersStockState: StateFlow<ViewState<List<UsersStockUiModel>>> get() = _usersStockState

    fun getStockFromWatchlist() {
        viewModelScope.launch {
            watchlistStockRepositoryImpl.getWatchlistStock()
            _watchlistStockState.resetViewState()
            watchlistStockRepositoryImpl.stockState.collect {
                when (it) {
                    is Resource.Success -> {
                        d("stocks_vm","${it.data}")
                        _watchlistStockState.emit(_watchlistStockState.value.copy(data = it.data.map { it.toCurrentStockUiModel() }))
                    }
                    is Resource.Error -> {
                        _watchlistStockState.emit(_watchlistStockState.value.copy(error = it.error))
                    }
                    is Resource.Loading -> TODO()
                }
            }
        }
    }

    fun getUsersStocks(){
        viewModelScope.launch {
            usersStockRepositoryImpl.getUsersStock()
            _usersStockState.resetViewState()
            usersStockRepositoryImpl.stockState.collect{
                when(it){
                    is Resource.Success ->{
                        _usersStockState.emit(_usersStockState.value.copy(it.data.map { it.toUserStockUiModel() }))
                    }
                    is Resource.Error -> {
                        _usersStockState.emit(_usersStockState.value.copy(error = it.error))
                    }
                    else -> {}
                }
            }
        }
    }
}