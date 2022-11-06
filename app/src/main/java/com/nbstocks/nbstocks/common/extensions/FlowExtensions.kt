package com.nbstocks.nbstocks.common.extensions

import androidx.viewbinding.ViewBinding
import com.nbstocks.nbstocks.presentation.model.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

suspend fun <T: ViewState<D>, D> Flow<T>.collectViewState(binding: ViewBinding, block: (data: D) -> Unit) {
    collect{ viewState ->
        viewState.data?.let {data ->
            block(data)
        }
        viewState.error?.let {error ->
            error.localizedMessage?.let { it1 -> binding.root.makeSnackbar(it1, true) }
        }
    }
}

fun <D> MutableStateFlow<ViewState<D>>.emitSuccessViewState(
    scope: CoroutineScope,
    block: () -> D
): MutableStateFlow<ViewState<D>> {

    scope.launch {
        this@emitSuccessViewState.emit(
            this@emitSuccessViewState.value.copy(data = block())
        )
    }
    return this
}

fun <D> MutableStateFlow<ViewState<D>>.emitErrorViewState(
    scope: CoroutineScope,
    block: () -> Throwable
): MutableStateFlow<ViewState<D>> {

    scope.launch {
        this@emitErrorViewState.emit(
            this@emitErrorViewState.value.copy(error = block())
        )
    }
    return this
}

fun <T> MutableStateFlow<ViewState<T>>.resetViewState() {
    value = this.value.copy(data = null, error = null)
}