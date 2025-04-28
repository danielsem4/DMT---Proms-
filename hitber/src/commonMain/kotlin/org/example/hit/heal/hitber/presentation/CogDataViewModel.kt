package org.example.hit.heal.hitber.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.hit.heal.hitber.core.domain.DataError
import org.example.hit.heal.hitber.data.CogDataRepository
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.core.domain.Result

class CogDataViewModel(private val repository: CogDataRepository) : ViewModel() {

    private val _result = MutableStateFlow<Result<Unit, DataError.Remote>?>(null)

    fun uploadCogData(cogData: CogData) {
        viewModelScope.launch {
            val result = repository.uploadMeasureResponse(cogData)
            _result.emit(result)
        }
    }
}
