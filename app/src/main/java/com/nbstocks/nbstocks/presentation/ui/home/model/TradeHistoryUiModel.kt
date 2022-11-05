package com.nbstocks.nbstocks.presentation.ui.home.model


data class TradeHistoryUiModel(
    val isBuy: Boolean = false,
    val symbol:String = "",
    val money:String = "",
    val tradeDate:String = ""
)