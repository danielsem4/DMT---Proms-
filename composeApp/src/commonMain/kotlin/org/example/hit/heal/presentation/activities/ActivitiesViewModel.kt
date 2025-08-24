package org.example.hit.heal.presentation.activities

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import core.data.model.ActivityItem
import core.data.storage.Storage
import core.domain.api.AppApi
import core.utils.getCurrentDate
import core.utils.getCurrentFormattedDateTime
import core.utils.getCurrentTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import kotlin.time.Clock

/**
 * Activities viewmodel, handle the fetch of activities, upload and more
 */

class ActivitiesViewModel(
    private val storage: Storage,
    private val api: AppApi
) : ViewModel(), KoinComponent {

    private val _activities = MutableStateFlow<List<ActivityItem>>(emptyList())
    val activities: StateFlow<List<ActivityItem>> = _activities.asStateFlow()

    var selectedDate by mutableStateOf(
        getCurrentDate()
    )

    var selectedTime by mutableStateOf(
        getCurrentTime()
    )

    fun updateDate(newDate: String) {
        selectedDate = newDate
    }

    fun updateTime(newTime: String) {
        selectedTime = newTime
    }

    init {
        _activities.value = listOf(
            ActivityItem(id = 1, name = "Walk", description = null),
            ActivityItem(id = 2, name = "Run", description = null),
            ActivityItem(id = 3, name = "Yoga", description = null),
            ActivityItem(id = 4, name = "Swim", description = null)
        )
    }

}