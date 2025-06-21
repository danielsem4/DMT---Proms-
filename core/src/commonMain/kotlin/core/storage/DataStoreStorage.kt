package core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * Implementation of the Storage interface using a DataStore as the underlying mechanism.
 * Provides functionality to interact with persistence storage for reading, writing, and observing key-value pairs.
 *
 * This class allows retrieving values either as a Flow or directly through suspendable functions,
 * enabling both reactive and traditional access patterns.
 */

class DataStoreStorage(
    private val dataStore: DataStore<Preferences>
): Storage {

    override fun <T> getAsFlow(key: Storage.Key<T>): Flow<T?> = dataStore.data
        .map { preferences ->
            preferences[getDataStoreKey(key)] ?: key.default
        }

    override suspend fun <T> get(key: Storage.Key<T>): T? = getAsFlow(key).firstOrNull() ?: key.default

    override suspend fun <T> writeValue(key: Storage.Key<T>, value: T?) {
        dataStore.edit {

            val dataStoreKey = getDataStoreKey(key)
            if (value == null) {
                it.remove((dataStoreKey))
            } else {
                it[dataStoreKey] = value
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getDataStoreKey(key: Storage.Key<T>): Preferences.Key<T> = when (key) {

        is Storage.Key.BooleanKey -> booleanPreferencesKey(key.key)
        is Storage.Key.LongKey -> floatPreferencesKey(key.key)
        is Storage.Key.IntKey -> intPreferencesKey(key.key)
        is Storage.Key.StringKey -> stringPreferencesKey(key.key)
    } as Preferences.Key<T>
}