// Platform identification (iOS or Android)

package com.example.finalprojectnew

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

