package org.example.hit.heal.core.presentation.utils


import core.domain.DataError
import org.example.hit.heal.core.presentation.Resources.String.diskFull
import org.example.hit.heal.core.presentation.Resources.String.localUnknown
import org.example.hit.heal.core.presentation.Resources.String.noInternet
import org.example.hit.heal.core.presentation.Resources.String.remoteUnknown
import org.example.hit.heal.core.presentation.Resources.String.requestTimeout
import org.example.hit.heal.core.presentation.Resources.String.serializationError
import org.example.hit.heal.core.presentation.Resources.String.serverError
import org.example.hit.heal.core.presentation.Resources.String.unexpectedError
import org.example.hit.heal.core.presentation.Resources.String.tooManyRequests
import org.jetbrains.compose.resources.StringResource

fun DataError.toUiText(): StringResource {
    val stringRes = when (this) {
        // Local
        DataError.Local.DISK_FULL -> diskFull
        DataError.Local.UNKNOWN -> localUnknown

        // Remote
        DataError.Remote.REQUEST_TIMEOUT -> requestTimeout
        DataError.Remote.TOO_MANY_REQUESTS -> tooManyRequests
        DataError.Remote.SERVER -> serverError
        DataError.Remote.SERIALIZATION -> serializationError
        DataError.Remote.NO_INTERNET -> noInternet
        DataError.Remote.UNKNOWN -> remoteUnknown

        else -> unexpectedError
    }
    return stringRes
}