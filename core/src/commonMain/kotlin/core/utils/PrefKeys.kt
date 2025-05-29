package core.util

import core.data.storage.Storage

object PrefKeys {
    val clinicId = Storage.Key.IntKey(key = "clinic_id", default = null)
    val serverUrl = Storage.Key.StringKey(key = "server_url", default = null)
    val token = Storage.Key.StringKey(key = "token", default = null)
    val userId = Storage.Key.StringKey(key = "user_id", default = null)
}