@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.example.hit.heal.cdt.di

import io.ktor.client.engine.darwin.Darwin
import org.example.hit.heal.cdt.utils.network.createHttpClient

actual object HttpClientFactory {
    actual fun create() = createHttpClient(Darwin.create())
}