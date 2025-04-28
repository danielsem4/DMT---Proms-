//package org.example.hit.heal.hitber.presentation
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.launch
//import org.example.hit.heal.hitber.core.domain.DataError
//import org.example.hit.heal.hitber.data.ImageUploadRepository
//import org.example.hit.heal.hitber.data.model.CogData
//import org.example.hit.heal.hitber.utils.getNow
//import org.example.hit.heal.hitber.core.domain.Result
//
//class ImageUploadViewModel(
//    private val repository: ImageUploadRepository
//) : ViewModel() {
//
//    private val _imageResult = MutableStateFlow<Result<Unit, DataError.Remote>?>(null)
//
//    fun uploadImage(
//        cogData: CogData
//    ) {
//        viewModelScope.launch {
//                val fileName = ".png"
//
//                    repository.uploadImage(
//                        fileBytes = ,
//                        fileName = fileName,
//                        clinicId = cogData.clinicId,
//                        patientId = cogData.patientId,
//                        measurement = cogData.measurement,
//                        date = getNow(),
//                        testVersion = ""
//                    )
//                }
//                }}
//
//
//sealed class QuestionType {
//    object SixthQuestion : QuestionType()
//    object SeventhQuestion : QuestionType()
//    object TenthQuestion : QuestionType()
//}