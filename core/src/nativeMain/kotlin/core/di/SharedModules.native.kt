package core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import core.data.local.createDataStore
import core.data.storage.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModuleCore: Module
    get() = module {
        single<DataStore<Preferences>> {
            createDataStore()
        }
    }
