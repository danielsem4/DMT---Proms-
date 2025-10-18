package com.example.finalprojectnew.assessment

// holds the results of stage1 in memory until stage2 is completed.
object SessionRecorder {
    private var _stage1: Stage1Result? = null

    val stage1: Stage1Result? get() = _stage1

    fun recordStage1(result: Stage1Result) { _stage1 = result }

    fun clearStage1() { _stage1 = null } // clean the results after save stage1+stage2
}
