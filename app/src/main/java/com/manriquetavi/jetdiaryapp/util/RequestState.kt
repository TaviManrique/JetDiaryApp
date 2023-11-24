package com.manriquetavi.jetdiaryapp.util

sealed interface RequestState<out T> {
    object Loading: RequestState<Nothing>
    data class Success<T>(val data: T): RequestState<T>
    data class Error(val error: Throwable): RequestState<Nothing>
}
