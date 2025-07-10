package presentation.dialScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import core.domain.use_case.PlayAudioUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.core.presentation.Resources.String.callToDentist
import org.example.hit.heal.core.presentation.Resources.String.callToDetistPass
import org.example.hit.heal.core.presentation.Resources.String.dentistNumberShowenCallHimPass
import org.example.hit.heal.core.presentation.Resources.String.dentistPass
import org.example.hit.heal.core.presentation.Resources.String.dermatologistPass
import org.example.hit.heal.core.presentation.Resources.String.dialeToDentist
import org.example.hit.heal.core.presentation.Resources.String.dialerOpened
import org.example.hit.heal.core.presentation.Resources.String.dialerOpenedPass
import org.example.hit.heal.core.presentation.Resources.String.dialerPageDentistNumberAppeared
import org.example.hit.heal.core.presentation.Resources.String.dialerPageSecondAssist
import org.example.hit.heal.core.presentation.Resources.String.dialerPageThirdAssist
import org.example.hit.heal.core.presentation.Resources.String.dialerPageWrongActionOne
import org.example.hit.heal.core.presentation.Resources.String.familyDoctorPass
import org.example.hit.heal.core.presentation.Resources.String.neurologistPass
import org.example.hit.heal.core.presentation.Resources.String.ophthalmologistPass
import org.example.hit.heal.core.presentation.Resources.String.orthopedistPass
import org.example.hit.heal.core.presentation.Resources.String.paintClinicPass
import org.example.hit.heal.core.presentation.Resources.String.pressTheDialButtonThatShowenDownPass
import org.example.hit.heal.core.presentation.Resources.String.psychiatristPass
import org.example.hit.heal.core.presentation.Resources.String.searchDentistNumberPass
import org.example.hit.heal.core.presentation.Resources.String.secondPartOfTestInstructionsPass
import org.example.hit.heal.core.presentation.Resources.String.whatDoYouNeedToDoPass
import org.example.hit.heal.core.presentation.Resources.String.whatYouNeedToDo
import org.example.hit.heal.core.presentation.Resources.String.wrongNumberDialedPleaseTryAgainPass
import presentation.components.CountdownDialogHandler
import presentation.dialScreen.components.StartCheckingIfUserDidSomethingUseCase
import presentation.endScreen.EndScreen

class DialScreenViewModel(
    private val countdownDialogHandler: CountdownDialogHandler,
    private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

    // --- Contacts list ---
    val contacts = listOf(
        dentistPass,
        familyDoctorPass,
        paintClinicPass,
        dermatologistPass,
        orthopedistPass,
        neurologistPass,
        ophthalmologistPass,
        psychiatristPass
    )

    // --- StateFlows for UI state ---
    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    private val _enteredNumber = MutableStateFlow("")
    val enteredNumber: StateFlow<String> = _enteredNumber

    val isPlaying = playAudioUseCase.isPlaying

    private val _showUnderstandingDialog = MutableStateFlow(false)
    val showUnderstandingDialog: StateFlow<Boolean> = _showUnderstandingDialog

    private val _isCloseIconDialog = MutableStateFlow(false)
    val isCloseIconDialog: StateFlow<Boolean> = _isCloseIconDialog

    private val _isNextScreen = MutableStateFlow(true)
    val isNextScreen: StateFlow<Boolean> = _isNextScreen

    private val _nextScreen = MutableStateFlow<Screen?>(null)
    val nextScreen = _nextScreen.asStateFlow()

    private val _currentMissionPass = MutableStateFlow(1)
    val currentMissionPass: StateFlow<Int> = _currentMissionPass

    // --- Reminder and instruction tracking ---
    private var didNothingFirstTime = 0
    private var didNothingSecondTime = 0
    private var didNothingThirdTime = 0
    private var wrongNumberFirstTime = 0
    private var wrongNumberSecondTime = 0
    private var isSecondInstructions = false

    // --- Countdown dialog and audio handler ---

    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive


    // --- Initialization ---
    init {
        startDialogInstructions()
    }

    // --- UI Actions ---
    fun onNumberClicked(number: String) {
        _enteredNumber.value += number
    }

    fun deleteLastDigit() {
        if (_enteredNumber.value.isNotEmpty()) {
            _enteredNumber.value = _enteredNumber.value.dropLast(1)
        }
    }

    fun toggleDialog() {
        _isDialogVisible.value = !_isDialogVisible.value
    }

    fun onPlayAudioRequested(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }

    fun onUnderstandingConfirmed() {
        _showUnderstandingDialog.value = false
    }

    fun onUnderstandingDenied() {
        _showUnderstandingDialog.value = false
        isSecondInstructions = true
        didNothingFirstTime--
        startDialogInstructions()
    }

    fun onUserClickedDialButton() {
        _currentMissionPass.value = 2
        _isCloseIconDialog.value = true

        stopChecks()
        countdownDialogHandler.showCountdownDialog(
            isPlayingFlow = isPlaying,
            audioText = dialerOpened to dialerOpenedPass
        )
    }

    fun checkCorrectNumber(correctNumber: String) {
        val enteredNumberTrimmed = _enteredNumber.value.trim()

        if (enteredNumberTrimmed == correctNumber) {
            navigateToEndScreen()
            return
        }

        when (_currentMissionPass.value) {
            2 -> handleFirstPass()
            3 -> handleSecondPass()
        }
    }

    private fun handleFirstPass() {
        when (++wrongNumberFirstTime) {
            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = dialerPageWrongActionOne to wrongNumberDialedPleaseTryAgainPass
            )

            2 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying,
                    audioText = dialerPageDentistNumberAppeared to dentistNumberShowenCallHimPass
                )
                _currentMissionPass.value = 3
                stopChecks()
            }
        }
    }

    private fun handleSecondPass() {
        when (++wrongNumberSecondTime) {
            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = dialerPageWrongActionOne to wrongNumberDialedPleaseTryAgainPass
            )

            2 -> navigateToEndScreen()
        }
    }

    fun clearNextScreen() {
        _nextScreen.value = null
    }

    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()

        when (_currentMissionPass.value) {
            1 -> startFirstCheck()
            2 -> startSecondCheck()
            3 -> startThirdCheck()
        }
    }

    // --- Private helpers for reminders and instructions ---

    private fun startDialogInstructions() {
        getReminderDidNotingTextFirstTime()
        if (!isSecondInstructions) {
            _showUnderstandingDialog.value = true
        } else {
            _isCloseIconDialog.value = true
        }
    }

    private val useCase = StartCheckingIfUserDidSomethingUseCase(
        coroutineScope = viewModelScope,
        getReminderDidNotingText = { getReminderDidNotingTextFirstTime() },
        updateCloseIconDialog = { show -> _isCloseIconDialog.value = show }
    )

    fun startFirstCheck() {
        useCase.start(
            getDidNothingCount = { didNothingFirstTime },
            maxAttempts = 3,
        )
    }

    private val useCaseSecond = StartCheckingIfUserDidSomethingUseCase(
        coroutineScope = viewModelScope,
        getReminderDidNotingText = { getReminderDidNotingTextSecondTime() },
        updateCloseIconDialog = { show -> _isCloseIconDialog.value = show }
    )

    private fun startSecondCheck() {
        useCaseSecond.start(
            getDidNothingCount = { didNothingSecondTime },
            maxAttempts = 3,
        )
    }

    private val useCaseThird = StartCheckingIfUserDidSomethingUseCase(
        coroutineScope = viewModelScope,
        getReminderDidNotingText = { getReminderDidNotingTextThirdTime() },
        updateCloseIconDialog = { show -> _isCloseIconDialog.value = show }
    )

    private fun startThirdCheck() {
        useCaseThird.start(
            getDidNothingCount = { didNothingThirdTime },
            maxAttempts = 3,
        )
    }

    private fun stopChecks() {
        useCase.stop()
        useCaseSecond.stop()
        useCaseThird.stop()
    }

    private fun getReminderDidNotingTextFirstTime() {
        when (didNothingFirstTime++) {
            0 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = callToDentist to secondPartOfTestInstructionsPass
            )

            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = whatYouNeedToDo to whatDoYouNeedToDoPass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = dialerPageThirdAssist to pressTheDialButtonThatShowenDownPass
            )

            3 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying,
                    audioText = dialerOpened to dialerOpenedPass
                )
                stopChecks()
                _currentMissionPass.value = 2
            }
        }
    }

    private fun getReminderDidNotingTextSecondTime() {
        when (++didNothingSecondTime) {
            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = whatYouNeedToDo to whatDoYouNeedToDoPass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = dialerPageSecondAssist to searchDentistNumberPass
            )

            3 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying,
                    audioText = dialerPageDentistNumberAppeared to dentistNumberShowenCallHimPass
                )
                stopChecks()
                _currentMissionPass.value = 3
            }
        }
    }

    private fun getReminderDidNotingTextThirdTime() {
        when (++didNothingThirdTime) {
            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = whatYouNeedToDo to whatDoYouNeedToDoPass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = dialeToDentist to callToDetistPass
            )

            3 -> {
                navigateToEndScreen()
            }
        }
    }

    private fun navigateToEndScreen() {
        _isNextScreen.value = true
        _nextScreen.value = EndScreen()
    }

    fun stopAll() {
        println("didNothingFirstTime: $didNothingFirstTime")
        println("didNothingSecondTime: $didNothingSecondTime")
        println("didNothingThirdTime: $didNothingThirdTime")
        println("wrongNumberFirstTime: $wrongNumberFirstTime")
        println("wrongNumberSecondTime: $wrongNumberSecondTime")
        playAudioUseCase.stopAudio()
        countdownDialogHandler.hideDialog()
        stopChecks()
    }
}
