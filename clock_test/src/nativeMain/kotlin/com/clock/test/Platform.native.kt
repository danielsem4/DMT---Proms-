package com.clock.test

import platform.UIKit.UIDevice

actual class Platform {
    actual fun getName(): String {
        return "iOS ${UIDevice.currentDevice.systemVersion}"
    }
}