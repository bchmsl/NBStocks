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
