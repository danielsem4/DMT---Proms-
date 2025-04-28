package org.example.hit.heal.hitber.core.domain

sealed interface DataError : ResultError {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN;
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN;
    }
}

