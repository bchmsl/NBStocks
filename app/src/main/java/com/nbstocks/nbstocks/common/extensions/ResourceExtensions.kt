package com.nbstocks.nbstocks.common.extensions

import com.nbstocks.nbstocks.common.handlers.Resource

fun <T>Resource<T>.doOnSuccess(block: (T) -> Unit): Resource<T> {
    if (this is Resource.Success){
        block(this.data)
    }
    return this
}

fun <T>Resource<T>.doOnFailure(block: (Throwable) -> Unit): Resource<T>{
    if (this is Resource.Error){
        block(this.error)
    }
    return this
}