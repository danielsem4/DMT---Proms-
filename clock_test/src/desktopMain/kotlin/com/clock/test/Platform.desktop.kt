package com.clock.test

actual class Platform {
    actual fun getName(): String {
        return "Desktop"
    }
}