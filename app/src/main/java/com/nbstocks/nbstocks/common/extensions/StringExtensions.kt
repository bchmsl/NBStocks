package com.nbstocks.nbstocks.common.extensions

import com.nbstocks.nbstocks.common.constants.StockTypeNames
import com.nbstocks.nbstocks.common.stocktype.StockType

fun String.toStockType(): StockType {
    return when (this) {
        StockTypeNames.TYPE_DEPOSITARY -> StockType.DEPOSITARY
        StockTypeNames.TYPE_COMMON -> StockType.COMMON
        StockTypeNames.TYPE_PREFERRED -> StockType.PREFERRED
        StockTypeNames.TYPE_UNIT -> StockType.UNIT
        StockTypeNames.TYPE_REIT -> StockType.REIT
        StockTypeNames.TYPE_LIMITED -> StockType.LIMITED
        StockTypeNames.TYPE_RIGHT -> StockType.RIGHT
        StockTypeNames.TYPE_TRUST -> StockType.TRUST
        else -> StockType.OTHER
    }
}

fun String.toMonthDay(): String {
    val date = this.split("-")
    val month = date[1].toInt()
    val day = date[2].toInt()
    return when (month) {
        1 -> "Jan $day"
        2 -> "Feb $day"
        3 -> "Mar $day"
        4 -> "Apr $day"
        5 -> "May $day"
        6 -> "Jun $day"
        7 -> "Jul $day"
        8 -> "Aug $day"
        9 -> "Sep $day"
        10 -> "Oct $day"
        11 -> "Nov $day"
        12 -> "Dec $day"
        else -> "Jan $day"
    }
}
