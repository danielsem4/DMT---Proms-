package core.data.storage

import android.content.Context

fun createDataStore(context: Context) = createDataStore(
    producePath = {
        context.filesDir.resolve(dataStoreFileName).absolutePath
    }
)