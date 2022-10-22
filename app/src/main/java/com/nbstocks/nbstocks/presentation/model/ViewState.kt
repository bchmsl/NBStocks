package com.nbstocks.nbstocks.presentation.model

import kotlinx.coroutines.flow.MutableStateFlow

data class ViewState<T>(
    val data: T? = null,
    val error: Throwable? = null
)

fun <T> MutableStateFlow<ViewState<T>>.resetViewState() {
    value = this.value.copy(data = null, error = null)
}