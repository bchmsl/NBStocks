package com.nbstocks.nbstocks.data.mapper

import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.UsersStockUiModel


fun UsersStockDomainModel.toUserStockUiModel() = UsersStockUiModel(
    symbol = symbol,
    price = price,
    amountInStocks = amountInStocks
)

fun UsersStockUiModel.toUserStockDomainModel() = UsersStockDomainModel(
    symbol = symbol,
    price = price,
    amountInStocks = amountInStocks
)