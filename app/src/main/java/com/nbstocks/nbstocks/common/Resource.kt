package com.nbstocks.nbstocks.common

sealed class Resource<T>(open val isLoading: Boolean = true) {
    class Success<T>(val data: T): Resource<T>()
    class Error<T>(val error: Throwable): Resource<T>()
    class Loading<T>(override val isLoading:Boolean): Resource<T>(isLoading)
}