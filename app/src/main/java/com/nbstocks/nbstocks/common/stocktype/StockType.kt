package com.nbstocks.nbstocks.common.stocktype

import android.graphics.Color
import com.nbstocks.nbstocks.common.constants.StockTypeNames

enum class StockType(val typeName: String, val color: Int) {
    DEPOSITARY(StockTypeNames.TYPE_DEPOSITARY, StockTypeColors.DEPOSITARY),
    COMMON(StockTypeNames.TYPE_COMMON, StockTypeColors.COMMON),
    PREFERRED(StockTypeNames.TYPE_PREFERRED, StockTypeColors.PREFERRED),
    UNIT(StockTypeNames.TYPE_UNIT, StockTypeColors.UNIT),
    REIT(StockTypeNames.TYPE_REIT, StockTypeColors.REIT),
    LIMITED(StockTypeNames.TYPE_LIMITED, StockTypeColors.LIMITED),
    RIGHT(StockTypeNames.TYPE_RIGHT, StockTypeColors.RIGHT),
    TRUST(StockTypeNames.TYPE_TRUST, StockTypeColors.TRUST),
    OTHER(StockTypeNames.TYPE_OTHER, StockTypeColors.OTHER);
}

object StockTypeColors {
    val DEPOSITARY = Color.parseColor("#4cd137")
    val COMMON = Color.parseColor("#0097e6")
    val PREFERRED = Color.parseColor("#00a8ff")
    val UNIT = Color.parseColor("#e84118")
    val REIT = Color.parseColor("#c23616")
    val LIMITED = Color.parseColor("#44bd32")
    val RIGHT = Color.parseColor("#40739e")
    val TRUST = Color.parseColor("#9c88ff")
    val OTHER = Color.parseColor("#718093")
}