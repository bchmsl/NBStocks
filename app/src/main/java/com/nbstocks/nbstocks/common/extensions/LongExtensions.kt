package com.nbstocks.nbstocks.common.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(): String {
    val date = Date(this * 1000)
    val format = SimpleDateFormat("dd MMM", Locale.getDefault())
    return format.format(date)
}