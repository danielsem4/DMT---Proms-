package core.domain

sealed interface DataError : Error {

    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        FORBIDDEN,
        SERIALIZATION,
        UNKNOWN,
        NOT_FOUND
    }

    enum class Local : DataError {
        DISK_FULL,
        EMPTY_FILE,
        UNKNOWN;
    }

}