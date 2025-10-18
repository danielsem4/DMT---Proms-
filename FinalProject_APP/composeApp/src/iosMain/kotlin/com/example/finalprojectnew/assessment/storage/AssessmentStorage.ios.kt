package com.example.finalprojectnew.assessment.storage

import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.Foundation.stringByAppendingPathComponent

actual object AssessmentStorage {
    actual suspend fun writeJson(dirName: String, fileName: String, content: String): String {
        val paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)
        val docsDir = paths.firstObject as String
        val dirPath = (docsDir as NSString).stringByAppendingPathComponent(dirName)
        val fm = NSFileManager.defaultManager
        if (!fm.fileExistsAtPath(dirPath)) {
            fm.createDirectoryAtPath(dirPath, withIntermediateDirectories = true, attributes = null, error = null)
        }
        val filePath = (dirPath as NSString).stringByAppendingPathComponent(fileName)
        val data = (content as NSString).dataUsingEncoding(NSUTF8StringEncoding)!!
        data.writeToFile(filePath, atomically = true)
        return filePath
    }
}
