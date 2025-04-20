package org.example.hit.heal.evaluations.domain.api

import io.ktor.client.engine.HttpClientEngineFactory

expect fun platformHttpEngine(): HttpClientEngineFactory<*>
