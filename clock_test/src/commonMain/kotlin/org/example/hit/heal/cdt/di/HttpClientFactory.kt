@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.example.hit.heal.cdt.di

import io.ktor.client.HttpClient

expect object HttpClientFactory {
    fun create(): HttpClient
}
