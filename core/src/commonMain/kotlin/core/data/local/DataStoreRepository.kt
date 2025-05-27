package core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

import androidx.datastore.preferences.core.stringPreferencesKey
import core.data.model.SuccessfulLoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map


class DataStoreRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val TIMESTAMP_KEY = longPreferencesKey(name = "saved_timestamp")
        val TOKEN_KEY = stringPreferencesKey("token")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val EMAIL_KEY = stringPreferencesKey("email")
        val IMAGE = stringPreferencesKey("image")
        val RESERCH_PATIENT = booleanPreferencesKey("researchPatient")
        val FIRST_NAME = stringPreferencesKey("firstName")
        val FULL_NAME = stringPreferencesKey("fullName")
        val CLINIC_ID = intPreferencesKey("clinicId")
        val CLINIC_NAME = stringPreferencesKey("clinicName")
        val TOKEN = stringPreferencesKey("token")
        val MODULES = stringPreferencesKey("modules")

        val STATUS = stringPreferencesKey("status")
      val  SERVER_URL = stringPreferencesKey("server_url")
    }

    suspend fun saveLoginResponse(response: SuccessfulLoginResponse): Boolean =
        try {
            dataStore.edit { preferences ->
//                preferences[TOKEN_KEY] = response.token
                preferences[USER_ID_KEY] = response.userId
//                preferences[EMAIL_KEY] = response.email
                preferences[IMAGE] = response.image
//                preferences[RESERCH_PATIENT] = response.researchPatient
//                preferences[FIRST_NAME] = response.firstName
//                preferences[FULL_NAME] = response.fullName
                preferences[CLINIC_ID] = response.clinicId
//                preferences[CLINIC_NAME] = response.clinicName
                preferences[TOKEN] = response.token
//                preferences[MODULES] = response.modules.toString()
//                preferences[STATUS] = response.status
                preferences[SERVER_URL] = response.serverUrl
            }
            true
        } catch (e: Exception) {
            println("saveLoginResponse() Error: $e")
            false
        }
    fun readToken(): Flow<String?> =
        dataStore.data
            .map { preferences -> preferences[TOKEN_KEY] }

    fun readUserId(): Flow<String?> =
        dataStore.data
            .map { preferences -> preferences[USER_ID_KEY] }

    fun readEmail(): Flow<String?> =
        dataStore.data
            .map { preferences -> preferences[EMAIL_KEY] }
    fun  readImage(): Flow<String?> =
        dataStore.data
            .map { preferences -> preferences[IMAGE] }



    fun readTimestamp(): Flow<Long> =
        dataStore.data
            .catch { emptyFlow<Long>() }
            .map { preferences ->
                preferences[TIMESTAMP_KEY] ?: 0L
            }
}