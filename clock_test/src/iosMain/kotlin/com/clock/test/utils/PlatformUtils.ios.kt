package com.clock.test.utils

import platform.posix.exit

actual fun exitApplication() {
    exit(0)
}