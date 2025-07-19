package presentation.dialScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import core.domain.use_case.PlayAudioUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
import presentation.dialScreen.components.MissionStage
import utils.CountdownDialogHandler
import utils.StartCheckingIfUserDidSomethingUseCase
import presentation.endScreen.EndScreen

class DialScreenViewModel(
    private val countdownDialogHandler: CountdownDialogHandler,
    private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

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

    private val _currentMissionPass = MutableStateFlow(MissionStage.DialerNotOpened)
    val currentMissionPass: StateFlow<MissionStage> = _currentMissionPass

    // Reminder and instruction tracking
    private var didNothingFirstTime = -1
    private var didNothingSecondTime = 0
    private var didNothingThirdTime = 0
    private var wrongNumberFirstTime = 0
    private var wrongNumberSecondTime = 0
    private var isSecondInstructions = false


    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive

    // Adds a digit to the current entered number
    fun onNumberClicked(number: String) {
        _enteredNumber.value += number
    }

    // Removes the last digit from the entered number
    fun deleteLastDigit() {
        if (_enteredNumber.value.isNotEmpty()) {
            _enteredNumber.value = _enteredNumber.value.dropLast(1)
        }
    }

    fun toggleDialog() {
        _isDialogVisible.value = !_isDialogVisible.value
    }

    fun onPlayAudioRequested(audioText: String) {
        viewModelScope.launch {
            playAudioUseCase.playAudio(audioText)
        }
    }

    // Called when the user confirms they understood the instructions,
    // starts the first level when the dialer is not yet opened
    fun onUnderstandingConfirmed() {
        _showUnderstandingDialog.value = false
        startFirstCheck()
    }

    // Called when the user denies understanding the instructions,
    // it resets the dialog step and shows the reminder again
    fun onUnderstandingDenied() {
        _showUnderstandingDialog.value = false
        isSecondInstructions = true
        didNothingFirstTime--
        showReminderDidNothingDialerNotOpened()
        stopChecks()
    }

    private fun onUnderstandingDidNothing() {
        _showUnderstandingDialog.value = false
    }

    // Called when the user presses the dial button,
    // shows confirmation and updates state
    fun onUserClickedDialButton() {
        _currentMissionPass.value = MissionStage.DialerOpened

        stopChecks()
        countdownDialogHandler.showCountdownDialog(
            isPlayingFlow = isPlaying,
            audioText = dialerOpened to dialerOpenedPass
        )
    }

    // Checks if the number entered matches the correct one
    fun checkCorrectNumber(correctNumber: String) {
        val enteredNumberTrimmed = _enteredNumber.value.trim()

        if (enteredNumberTrimmed == correctNumber) {
            navigateToEndScreen()
            return
        }

        // If number is incorrect, show the appropriate reminder based on current stage
        when (_currentMissionPass.value) {
            MissionStage.DialerOpened -> showReminderWrongNumberDialerOpened()
            MissionStage.CorrectContactOnly -> showReminderWrongNumberCorrectContact()
            else -> {}
        }

    }

    // Display the dialogs with the correct message and audio
    // when user dialed wrong number and the dialer has opened
    private fun showReminderWrongNumberDialerOpened() {
        stopChecks()
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
                _currentMissionPass.value = MissionStage.CorrectContactOnly
                stopChecks()
            }
        }
    }

    // Display the dialogs with the correct message and audio when user dialed wrong number
    // and reached the step of seeing only the correct phone number to call
    private fun showReminderWrongNumberCorrectContact() {
        stopChecks()
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

    // close dialog and restarts the 15-second timer based on the current stage
    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()

        when (_currentMissionPass.value) {
            MissionStage.DialerNotOpened -> startFirstCheck()
            MissionStage.DialerOpened -> startSecondCheck()
            MissionStage.CorrectContactOnly -> startThirdCheck()
        }
    }


    // Start the understanding dialog asking the user to confirm understanding
    private fun startUnderstandingDialog() {
        _isCloseIconDialog.value = true

        if (!isSecondInstructions) {
            _showUnderstandingDialog.value = true
        }
    }

    // Phase 1: Dialer hasn't opened yet - starts an inactivity timer – triggers a reminder after 15 seconds of no user interaction.
    private val useCase = StartCheckingIfUserDidSomethingUseCase(
        coroutineScope = viewModelScope,
        getReminderDidNotingText = { showReminderDidNothingDialerNotOpened() },
        updateCloseIconDialog = { show -> _isCloseIconDialog.value = show }
    )

    fun startFirstCheck() {
        if (didNothingFirstTime == -1) {
            showReminderDidNothingDialerNotOpened()
            return
        }
        if (didNothingFirstTime == 0 && !_isCloseIconDialog.value) {
            startUnderstandingDialog()
        }

        useCase.start(
            getDidNothingCount = { didNothingFirstTime },
            maxAttempts = 3,
        )
    }

    // // Phase 2: Dialer is open - starts an inactivity timer – triggers a reminder after 15 seconds of no user interaction.
    private val useCaseSecond = StartCheckingIfUserDidSomethingUseCase(
        coroutineScope = viewModelScope,
        getReminderDidNotingText = { showReminderDidNothingDialerOpened() },
        updateCloseIconDialog = { show -> _isCloseIconDialog.value = show }
    )

    private fun startSecondCheck() {
        useCaseSecond.start(
            getDidNothingCount = { didNothingSecondTime },
            maxAttempts = 3,
        )
    }

    // Phase 3: Only the correct contact is visible - starts an inactivity timer – triggers a reminder after 15 seconds of no user interaction.
    private val useCaseThird = StartCheckingIfUserDidSomethingUseCase(
        coroutineScope = viewModelScope,
        getReminderDidNotingText = { showReminderDidNothingCorrectContact() },
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

    // Display the dialogs with the correct message and audio
    // This is triggered when the user hasn't done anything for 15 seconds
    // and dialer has not yet opened
    private fun showReminderDidNothingDialerNotOpened() {
        when (++didNothingFirstTime) {
            0 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = callToDentist to secondPartOfTestInstructionsPass
            )

            1 -> {
                onUnderstandingDidNothing()
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying,
                    audioText = whatYouNeedToDo to whatDoYouNeedToDoPass
                )
            }

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
                _currentMissionPass.value = MissionStage.DialerOpened

            }
        }
    }

    // Display the dialogs with the correct message and audio
    // This is triggered when the user hasn't done anything for 15 seconds
    // and the dialer has opened
    private fun showReminderDidNothingDialerOpened() {
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
                _currentMissionPass.value = MissionStage.CorrectContactOnly
            }
        }
    }

    // Display the dialogs with the correct message and audio
    // This is triggered when the user hasn't done anything for 15 seconds
    // and the user has reached the step of seeing only the correct phone number to call
    private fun showReminderDidNothingCorrectContact() {
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
        playAudioUseCase.stopAudio()
        countdownDialogHandler.hideDialog()
        stopChecks()
    }
}
