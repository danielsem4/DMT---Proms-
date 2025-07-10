package org.example.hit.heal.presentation.activities

import androidx.lifecycle.ViewModel
import core.data.model.ActivityItem
import core.data.storage.Storage
import core.domain.api.AppApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

/**
 * Activities viewmodel, handle the fetch of activities, upload and more
 */

class ActivitiesViewModel(
    private val storage: Storage,
    private val api: AppApi
) : ViewModel(), KoinComponent {

    private val _activities = MutableStateFlow<List<ActivityItem>>(emptyList())
    val activities: StateFlow<List<ActivityItem>> = _activities.asStateFlow()

    init {
        _activities.value = listOf(
            ActivityItem(id = 1, name = "Walk", description = null),
            ActivityItem(id = 2, name = "Run", description = null),
            ActivityItem(id = 3, name = "Yoga", description = null),
            ActivityItem(id = 4, name = "Swim", description = null)
        )
    }

}