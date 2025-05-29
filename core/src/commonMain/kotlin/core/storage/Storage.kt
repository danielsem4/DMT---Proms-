package core.data.storage

import kotlinx.coroutines.flow.Flow

/**
 *
 */

interface Storage {

    fun <T> getAsFlow(key: Key<T>): Flow<T?>

    suspend fun <T> get(key: Key<T>): T?

    suspend fun <T> writeValue(key: Key<T>, value: T?)

    suspend fun <T> clearValue(key: Key<T>) {
        writeValue(key, null)
    }

    sealed class Key<T>(
        val key: String,
        val default: T?
    ) {

        open class StringKey(key: String, default: String?) : Key<String>(key, default)
        open class IntKey(key: String, default: Int?) : Key<Int>(key, default)
        open class LongKey(key: String, default: Long?) : Key<Long>(key, default)
        open class BooleanKey(key: String, default: Boolean?) : Key<Boolean>(key, default)

    }
}