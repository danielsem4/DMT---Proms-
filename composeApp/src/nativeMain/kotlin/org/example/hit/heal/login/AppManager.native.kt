package org.example.hit.heal.login

import platform.posix.exit

actual class AppManager {
    actual fun exitApp() {
        exit(0)
    }
}