package com.nbstocks.nbstocks.common.extensions

fun <T> List<T>.safeSubList(subListSize: Int): List<T> {
    val returnList = mutableListOf<T>()
    this.forEachIndexed { index, t ->
        if (index <= subListSize - 1) {
            returnList.add(t)
        } else {
            return@forEachIndexed
        }
    }
    return returnList
}