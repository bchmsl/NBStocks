package com.nbstocks.nbstocks.presentation.model

data class ViewState<T>(
    val data: T? = null,
    val error: Throwable? = null
)

