package com.nbstocks.nbstocks.common.extensions

fun Double?.toPercentString(): String {
    try {
        var plusSign = ""
        if (this!! > 0) {
            plusSign = "+"
        }
        val firstPartString = this.times(100).toString().split(".")[0]
        val secondPartString = this.times(100).toString().split(".")[1].safeSubString(2)
        if (secondPartString.toInt() == 0) {
            return "$firstPartString%"
        }
        return "$plusSign$firstPartString.$secondPartString%"
    } catch (e: Exception) {
        return "--%"
    }
}

fun Double?.toCurrencyString(): String {
    try {
        val firstPartString = this.toString().split(".")[0]
        val secondPartString = this.toString().split(".")[1].safeSubString(2)
        if (secondPartString.toInt() == 0) {
            return "$$firstPartString"
        }
        return "$$firstPartString.$secondPartString"
    } catch (e: Exception) {
        return "--"
    }
}

fun String.safeSubString(size: Int): String {
    return if (this.length > size) {
        this.substring(0, size)
    } else {
        val returnValue = this
        for (i in 0 until size - this.length) {
            returnValue.plus("0")
        }
        returnValue
    }
}


fun String?.toCurrencyDouble(): Double {
    return try {
        this?.replace("$", "")?.toDouble() ?: 0.0
    } catch (e: Exception) {
        0.0
    }
}