package presentation.appsDeviceScreen

import androidx.compose.ui.input.key.Key.Companion.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.person_names
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getStringArray
import presentation.components.ContactData
import presentation.components.AppData

class AppDeviceViewModel : ViewModel() {

    // --- Dialog State ---
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _showSecondDialog = MutableStateFlow(false)
    val showSecondDialog: StateFlow<Boolean> = _showSecondDialog

    private val _showReminderDialog = MutableStateFlow(false)
    val showReminderDialog: StateFlow<Boolean> = _showReminderDialog

    private val _dialogText = MutableStateFlow("")
    val dialogText: StateFlow<String> = _dialogText

    // --- Countdown ---
    private val _countdown = MutableStateFlow(10)
    val countdown: StateFlow<Int> = _countdown

    // --- Contact & App State ---
    private val _contact = MutableStateFlow<ContactData?>(null)
    val contact: StateFlow<ContactData?> = _contact

    private val _currentScreen = MutableStateFlow(ScreenState.MainScreen)
    val currentScreen: StateFlow<ScreenState> = _currentScreen

    // --- User Interaction State ---
    private val _userDidSomething = MutableStateFlow(false)

    private val _didNothingApp = MutableStateFlow(0)
    val didNothingApp: StateFlow<Int> = _didNothingApp

    private val _didNothingMain = MutableStateFlow(0)
    val didNothingMain: StateFlow<Int> = _didNothingMain

    private val _wrongApp = MutableStateFlow(0)
    val wrongApp: StateFlow<Int> = _wrongApp

    // --- Navigation Events ---
    sealed class NavigationEvent {
        data object ToContactsScreen : NavigationEvent()
        data object BackToAppsScreen : NavigationEvent() // ← חדש
        data class ToWrongAppScreen(val app: AppData) : NavigationEvent()
    }


    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent

    // --- Internal ---
    private var dialogJob: Job? = null
    private var reminderJob: Job? = null
    private var isSecondDialogShown = false
    private var hasStartedDialogSequence = false

    // --- Public API ---

    fun triggerDialogSequenceIfNeeded() {
        if (!hasStartedDialogSequence) {
            hasStartedDialogSequence = true
            startDialogSequence()
        }
    }
    fun setContact(name: String, phoneNumber: String) {
        _contact.value = ContactData(name, phoneNumber)
    }


    fun onAppClicked(app: AppData) {
        if (_wrongApp.value == 3 || app.label == "אנשי קשר") {
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEvent.ToContactsScreen)
            }
        } else {
            _wrongApp.update { it + 1 }
            _currentScreen.value = ScreenState.WrongAppScreen
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEvent.ToWrongAppScreen(app))
            }
        }
    }

    fun onUserDidSomething() {
        _userDidSomething.value = true
        _showReminderDialog.value = false
        startReminderCountdown()
    }

    fun onUnderstandingConfirmed() {
        _showSecondDialog.value = false
        startReminderCountdown()
    }

    fun onUnderstandingDenied() {
        _showSecondDialog.value = false
        _showDialog.value = true
        isSecondDialogShown = true
        startCountdownThenShowSecondDialog()
    }

    fun hideReminderDialog() {
        _showReminderDialog.value = false
    }

    // --- Private Logic ---

    private fun startDialogSequence() {
        dialogJob?.cancel()
        _showDialog.value = true
        startCountdownThenShowSecondDialog()
    }

    private fun startCountdownThenShowSecondDialog() {
        dialogJob = viewModelScope.launch {
            for (i in 10 downTo 1) {
                _countdown.value = i
                delay(1000)
            }
            _showDialog.value = false
            if (!isSecondDialogShown) {
                _showSecondDialog.value = true
                isSecondDialogShown = true
            }
            startReminderCountdown()
        }
    }

    fun startReminderCountdown() {
        _userDidSomething.value = false
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            while (_didNothingMain.value < 3 && _didNothingApp.value < 2) {
                delay(15_000)

                if (!_userDidSomething.value) {
                    _dialogText.value = getReminderText()
                    _showReminderDialog.value = true
                }
            }


            if (_didNothingApp.value == 2 && _currentScreen.value == ScreenState.WrongAppScreen) {
                _dialogText.value = "הנה המספר של ${_contact.value?.name ?: ""}, תחייג אליה"
                _showReminderDialog.value = true
                _navigationEvent.emit(NavigationEvent.BackToAppsScreen)
            }

        }
    }

    private fun getReminderText(): String {
        return when (_currentScreen.value) {
            ScreenState.MainScreen -> {
                when (_didNothingMain.updateAndGet { it + 1 }) {
                    1 -> "מה יש לעשות?"
                    2 -> "תחפש את רשימת אנשי הקשר בטלפון"
                    3 -> "הנה המספר של ${_contact.value?.name ?: ""}, תחייג אליה"
                    else -> ""
                }
            }

            ScreenState.WrongAppScreen -> {
                when (_didNothingApp.updateAndGet { it + 1 }) {
                    1 -> "מה יש לעשות?"
                    2 -> "כפתור החזרה נמצא בחלק העליון של המסך בצד שמאל"
                    else -> ""
                }
            }
        }

    }
    fun backToAppsScreen() {
        viewModelScope.launch {
            _currentScreen.value = ScreenState.MainScreen
            _showReminderDialog.value = false
            _navigationEvent.emit(NavigationEvent.BackToAppsScreen)
        }
    }

}
