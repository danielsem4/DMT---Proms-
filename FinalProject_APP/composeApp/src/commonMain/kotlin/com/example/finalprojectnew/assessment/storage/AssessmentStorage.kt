package com.example.finalprojectnew.assessment.storage

// Multiplatform storage facade.
// 'expect' declares the API in commonMain; each platform (androidMain/iosMain/…)
// must provide its own 'actual' implementation that writes JSON to a file and
// returns the full saved path.

expect object AssessmentStorage {
    suspend fun writeJson(dirName: String, fileName: String, content: String): String
}
