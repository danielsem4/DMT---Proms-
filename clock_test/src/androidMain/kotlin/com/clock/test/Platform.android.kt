package com.clock.test

import android.content.Context
import android.os.Build

actual class Platform(private val context: Context) {
    actual fun getName(): String {
        return "Android ${Build.VERSION.SDK_INT} (${context.packageName})"
    }
}