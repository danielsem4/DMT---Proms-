package org.example.hit.heal.evaluations.domain.api

import io.ktor.client.engine.*

expect fun platformHttpEngine(): HttpClientEngineFactory<*>
