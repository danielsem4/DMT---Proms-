package core.util

import core.domain.DataError
import dmt_proms.core.generated.resources.Res
import org.jetbrains.compose.resources.StringResource

//fun DataError.toUiText(): StringResource {
//    val stringRes = when (this) {
//        // Local
//        DataError.Local.DISK_FULL -> Res.string.unknown_error
//        DataError.Local.UNKNOWN -> Res.string.unknown_error
//
//        // Remote
//        DataError.Remote.REQUEST_TIMEOUT -> Res.string.request_timeout
//        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.too_many_requests
//        DataError.Remote.SERVER -> Res.string.server_error
//        DataError.Remote.SERIALIZATION -> Res.string.serialization_error
//        DataError.Remote.NO_INTERNET -> Res.string.no_internet_connection
//        DataError.Remote.UNKNOWN -> Res.string.unknown_error
//
//    }
//    return stringRes
//}