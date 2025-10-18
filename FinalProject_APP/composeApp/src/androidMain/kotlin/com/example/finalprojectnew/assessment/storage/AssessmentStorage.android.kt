package com.example.finalprojectnew.assessment.storage

import android.content.Context
import java.io.File

actual object AssessmentStorage {
    private lateinit var appCtx: Context
    fun init(context: Context) { appCtx = context.applicationContext }

    actual suspend fun writeJson(dirName: String, fileName: String, content: String): String {
        check(::appCtx.isInitialized) { "AssessmentStorage.init(context) was not called." }
        val dir = File(appCtx.filesDir, dirName).apply { mkdirs() }
        val file = File(dir, fileName)
        file.writeText(content)
        return file.absolutePath
    }
}
