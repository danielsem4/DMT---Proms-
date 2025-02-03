package org.example.hit.heal

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform