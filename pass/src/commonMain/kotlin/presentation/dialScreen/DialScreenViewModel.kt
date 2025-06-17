package presentation.dialScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import core.domain.use_case.PlayAudioUseCase
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.call_to_dentist
import dmt_proms.pass.generated.resources.call_to_detist_pass
import dmt_proms.pass.generated.resources.dentist_number_showen_call_him_pass
import dmt_proms.pass.generated.resources.dentist_pass
import dmt_proms.pass.generated.resources.dermatologist_pass
import dmt_proms.pass.generated.resources.diale_to_dentist
import dmt_proms.pass.generated.resources.dialer_opened
import dmt_proms.pass.generated.resources.dialer_opened_pass
import dmt_proms.pass.generated.resources.dialer_page_dentis_number_appeared
import dmt_proms.pass.generated.resources.dialer_page_second_assist
import dmt_proms.pass.generated.resources.dialer_page_thired_assist
import dmt_proms.pass.generated.resources.dialer_page_wrong_action_one
import dmt_proms.pass.generated.resources.family_doctor_pass
import dmt_proms.pass.generated.resources.neurologist_pass
import dmt_proms.pass.generated.resources.ophthalmologist_pass
import dmt_proms.pass.generated.resources.orthopedist_pass
import dmt_proms.pass.generated.resources.paint_clinic_pass
import dmt_proms.pass.generated.resources.press_the_dial_button_that_showen_down_pass
import dmt_proms.pass.generated.resources.psychiatrist_pass
import dmt_proms.pass.generated.resources.search_dentist_number_pass
import dmt_proms.pass.generated.resources.second_part_of_test_instructions_pass
import dmt_proms.pass.generated.resources.what_do_you_need_to_do_pass
import dmt_proms.pass.generated.resources.what_you_need_to_do
import dmt_proms.pass.generated.resources.wrong_number_dialed_please_try_again_pass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import presentation.components.CountdownDialogHandler
import presentation.dialScreen.components.StartCheckingIfUserDidSomethingUseCase
import presentation.endScreen.EndScreen

class DialScreenViewModel(
    private val countdownDialogHandler: CountdownDialogHandler,
    private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

    // --- Contacts list ---
    val contacts = listOf(
        Res.string.dentist_pass,
        Res.string.family_doctor_pass,
        Res.string.paint_clinic_pass,
        Res.string.dermatologist_pass,
        Res.string.orthopedist_pass,
        Res.string.neurologist_pass,
        Res.string.ophthalmologist_pass,
        Res.string.psychiatrist_pass
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
            audioText = Res.string.dialer_opened to Res.string.dialer_opened_pass
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
                audioText = Res.string.dialer_page_wrong_action_one to Res.string.wrong_number_dialed_please_try_again_pass
            )

            2 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying,
                    audioText = Res.string.dialer_page_dentis_number_appeared to Res.string.dentist_number_showen_call_him_pass
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
                audioText = Res.string.dialer_page_wrong_action_one to Res.string.wrong_number_dialed_please_try_again_pass
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
            intervalSeconds = 15,
            showDialog = { showDialog.value }
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
            intervalSeconds = 15,
            showDialog = { showDialog.value }
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
            intervalSeconds = 15,
            showDialog = { showDialog.value }
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
                audioText = Res.string.call_to_dentist to Res.string.second_part_of_test_instructions_pass
            )

            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = Res.string.what_you_need_to_do to Res.string.what_do_you_need_to_do_pass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = Res.string.dialer_page_thired_assist to Res.string.press_the_dial_button_that_showen_down_pass
            )

            3 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying,
                    audioText = Res.string.dialer_opened to Res.string.dialer_opened_pass
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
                audioText = Res.string.what_you_need_to_do to Res.string.what_do_you_need_to_do_pass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = Res.string.dialer_page_second_assist to Res.string.search_dentist_number_pass
            )

            3 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying,
                    audioText = Res.string.dialer_page_dentis_number_appeared to Res.string.dentist_number_showen_call_him_pass
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
                audioText = Res.string.what_you_need_to_do to Res.string.what_do_you_need_to_do_pass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = Res.string.diale_to_dentist to Res.string.call_to_detist_pass
            )

            3 -> {
                navigateToEndScreen()
            }
        }
    }

    private fun navigateToEndScreen() {
        stopChecks()
        _isNextScreen.value = true
        _nextScreen.value = EndScreen()
    }
}
