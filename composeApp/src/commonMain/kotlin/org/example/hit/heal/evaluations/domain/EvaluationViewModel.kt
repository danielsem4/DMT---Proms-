package org.example.hit.heal.evaluations.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// ViewModel to Manage Evaluations
class EvaluationViewModel : ViewModel() {
    private val _evaluations = MutableStateFlow(
        listOf(
            Evaluation("Performance Review", "2024-02-13", Frequency.WEEKLY),
            Evaluation("Daily Check-in", "2024-02-12", Frequency.DAILY),
            Evaluation("Monthly Assessment", "2024-02-01", Frequency.MONTHLY),
            Evaluation("Hourly Update", "2024-02-13 10:00", Frequency.HOURLY),
            Evaluation("Project Sprint Review", "2024-03-01", Frequency.WEEKLY),
            Evaluation("Team Feedback Session", "2024-03-05", Frequency.MONTHLY),
            Evaluation("Code Review Session", "2024-02-20", Frequency.DAILY),
            Evaluation("Quarterly Business Review", "2024-04-01", Frequency.MONTHLY),
            Evaluation("Client Meeting", "2024-02-15", Frequency.WEEKLY),
            Evaluation("Health Checkup", "2024-06-10", Frequency.MONTHLY),
            Evaluation("Workout Progress", "2024-02-14", Frequency.DAILY),
            Evaluation("Security Audit", "2024-07-01", Frequency.MONTHLY),
            Evaluation("App Usage Report", "2024-02-13", Frequency.DAILY),
            Evaluation("Annual Performance Review", "2024-12-31", Frequency.WEEKLY),
            Evaluation("Social Media Metrics", "2024-02-15", Frequency.DAILY),
        )
    )
    val evaluations: StateFlow<List<Evaluation>> = _evaluations
}

/*
*
* {
*   "name" : "aaaaa",
*   "date" : "YYYY-MM-DD",
*   "frequency" : "WEEKLY"
* }
*
*
* */