package org.example.hit.heal.cdt.data.network

sealed class TransactionResult<out T, out E> {
    data class UploadFailure<E>(val error: E) : TransactionResult<Nothing, E>()
    data class SendFailure<E>(val error: E) : TransactionResult<Nothing, E>()
    data class Success<T>(val data: T) : TransactionResult<T, Nothing>()
}
