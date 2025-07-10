package com.example.new_memory_test.data.model

import core.data.model.MeasureObjectBoolean
import core.data.model.MeasureObjectString
import kotlinx.serialization.Serializable

@Serializable
data class MemoryData(
    var measurement: Int = 20,
    var patient_id: Int = 0,
    var date: String = "2030-12-12 12:12:12.6",
    var clinicId: Int = 0,
    var MemoryQuestionPart1: ArrayList<MemoryQuestionPart1> = ArrayList(),
    var images1: ArrayList<MeasureObjectString> = ArrayList(),
    var notificationsCount: List<MeasureObjectString> = emptyList(), // he don't do some fo 1 minutes
    var successRateAfter: List<MeasureObjectString> = emptyList(),//
    var PhoneCallResult: List<MeasureObjectBoolean> = emptyList(),// Why it is not
    var MemoryQuestionPart2: ArrayList<MemoryQuestionPart2> = ArrayList(),
    var images2: ArrayList<MeasureObjectString> = ArrayList(),
    var activitiesPlaced: ArrayList<ActivityPlacement> = ArrayList(),//schedule
    var imageUrl: ArrayList<MeasureObjectString> = ArrayList(),//What is this  schedule
    var MemoryQuestionPart3: ArrayList<MemoryQuestionPart2> = ArrayList(),
    var images3: ArrayList<MeasureObjectString> = ArrayList(),

    )
