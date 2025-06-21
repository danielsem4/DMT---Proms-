package core.data.storage

import androidx.datastore.core.DataStore
import java.io.File
import androidx.datastore.preferences.core.Preferences

fun createDataStore(): DataStore<Preferences> {
    val parentFolder = File(System.getProperty("user.dir"))
    if (!parentFolder.exists()) {
        parentFolder.mkdirs()
    }

    val preferencePath = File(parentFolder, dataStoreFileName)
    return createDataStore(
        producePath = { preferencePath.absoluteFile.path }
    )
}
