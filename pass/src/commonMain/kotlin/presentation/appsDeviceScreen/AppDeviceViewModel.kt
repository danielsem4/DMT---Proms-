package presentation.appsDeviceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.example.hit.heal.core.presentation.Colors
import presentation.components.CountdownDialogHandler
import presentation.components.AppData
import core.domain.use_case.PlayAudioUseCase
import kotlinx.coroutines.NonCancellable.isActive
import org.example.hit.heal.core.presentation.Resources.Icon.calculatorIcon
import org.example.hit.heal.core.presentation.Resources.Icon.cameraIcon
import org.example.hit.heal.core.presentation.Resources.Icon.clockIcon
import org.example.hit.heal.core.presentation.Resources.Icon.contactsIcon
import org.example.hit.heal.core.presentation.Resources.Icon.documentsIcon
import org.example.hit.heal.core.presentation.Resources.Icon.emailIcon
import org.example.hit.heal.core.presentation.Resources.Icon.purseIcon
import org.example.hit.heal.core.presentation.Resources.Icon.settingsIcon
import org.example.hit.heal.core.presentation.Resources.Icon.storeIcon
import org.example.hit.heal.core.presentation.Resources.Icon.weatherIcon
import org.example.hit.heal.core.presentation.Resources.Icon.whiteMessages
import org.example.hit.heal.core.presentation.Resources.Icon.whitePhone
import org.example.hit.heal.core.presentation.Resources.String.appsPageSecondAssist
import org.example.hit.heal.core.presentation.Resources.String.calculator
import org.example.hit.heal.core.presentation.Resources.String.callHanaCohenPass
import org.example.hit.heal.core.presentation.Resources.String.callToHanaCohenInstructionPass
import org.example.hit.heal.core.presentation.Resources.String.camera
import org.example.hit.heal.core.presentation.Resources.String.clock
import org.example.hit.heal.core.presentation.Resources.String.contacts
import org.example.hit.heal.core.presentation.Resources.String.email
import org.example.hit.heal.core.presentation.Resources.String.herePersonsNumber
import org.example.hit.heal.core.presentation.Resources.String.messages
import org.example.hit.heal.core.presentation.Resources.String.myFiles
import org.example.hit.heal.core.presentation.Resources.String.nowTheContactsListWillBeOpenedPass
import org.example.hit.heal.core.presentation.Resources.String.phone
import org.example.hit.heal.core.presentation.Resources.String.purse
import org.example.hit.heal.core.presentation.Resources.String.searchContactsListInThePhonePass
import org.example.hit.heal.core.presentation.Resources.String.settings
import org.example.hit.heal.core.presentation.Resources.String.store
import org.example.hit.heal.core.presentation.Resources.String.weather
import org.example.hit.heal.core.presentation.Resources.String.whatDoYouNeedToDoPass
import org.example.hit.heal.core.presentation.Resources.String.whatYouNeedToDo
import presentation.contatcts.ContactsScreen

class AppDeviceViewModel( private val countdownDialogHandler: CountdownDialogHandler,
                          private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

    val items = listOf(
        AppData(
            imageRes = calculatorIcon,
            circleColor = Colors.calculatorColor,
            label = calculator
        ),
        AppData(
            imageRes = settingsIcon,
            circleColor = Colors.settingsColor,
            label = settings
        ),
        AppData(
            imageRes = cameraIcon,
            circleColor = Colors.cameraColor,
            label = camera
        ),
        AppData(
            imageRes = emailIcon,
            circleColor = Colors.emailColor,
            label = email
        ),
        AppData(
            imageRes = storeIcon,
            circleColor = Colors.storeColor,
            label = store
        ),
        AppData(
            imageRes = clockIcon,
            circleColor = Colors.clockColor,
            label = clock
        ),
        AppData(
            imageRes = contactsIcon,
            circleColor = Colors.contactsColor,
            label = contacts
        ),
        AppData(
            imageRes = whiteMessages,
            circleColor = Colors.messagesColor,
            label = messages
        ),
        AppData(
            imageRes = purseIcon,
            circleColor = Colors.purseColor,
            label = purse
        ),
        AppData(
            imageRes = weatherIcon,
            circleColor = Colors.weatherColor,
            label = weather
        ),
        AppData(
            imageRes = documentsIcon,
            circleColor = Colors.documentsColor,
            label = myFiles
        ),
        AppData(
            imageRes = whitePhone,
            circleColor = Colors.phoneColor,
            label = phone
        )
    )


    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive

    private val _showUnderstandingDialog = MutableStateFlow(false)
    val showUnderstandingDialog: StateFlow<Boolean> = _showUnderstandingDialog

    private val _isCloseIconDialog = MutableStateFlow(true)
    val isCloseIconDialog: StateFlow<Boolean> = _isCloseIconDialog

    private val _isNextScreen = MutableStateFlow(true)
    val isNextScreen: StateFlow<Boolean> = _isNextScreen

    private val _nextScreen = MutableStateFlow<Screen?>(null)
    val nextScreen = _nextScreen.asStateFlow()

    private var didNothing = 0
    private var wrongApp = 0
    private var isSecondInstructions = false

    private var reminderJob: Job? = null
    private var elapsedSeconds = 0

    val isPlaying = playAudioUseCase.isPlaying


    init {
        startDialogInstructions()
    }

    private fun startDialogInstructions() {
        _isCloseIconDialog.value = false
        getReminderDidNotingText()

        if (!isSecondInstructions) {
            _showUnderstandingDialog.value = true
        } else {
            _isCloseIconDialog.value = true
        }
    }


    fun startCheckingIfUserDidSomething() {
        println("viewModelScope isActive: ${viewModelScope.coroutineContext[Job]?.isActive}")

        reminderJob = viewModelScope.launch {
            println("Coroutine started")
            while (isActive && didNothing <= 3) {

                delay(1_000)
                if (showDialog.value) {
                    continue
                }

                elapsedSeconds++

                if (elapsedSeconds >= 15) {
                    getReminderDidNotingText()
                    _isCloseIconDialog.value = true
                }
            }
        }
    }

    fun onPlayAudioRequested(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }

    fun onUnderstandingConfirmed() {
        _showUnderstandingDialog.value = false
        elapsedSeconds = 0
    }

    fun onUnderstandingDenied() {
        _showUnderstandingDialog.value = false
        isSecondInstructions = true
        didNothing--
        startDialogInstructions()
    }


    fun onAppClicked(app: AppData) {
        if (app.label == contacts) {
            _nextScreen.value = ContactsScreen()
            return
        }
        wrongApp++

        _nextScreen.value = if (wrongApp == 3) {
            ContactsScreen()
        } else {
            WrongAppCache.lastWrongApp = app
            WrongAppScreen()
        }
    }


    private fun getReminderDidNotingText() {
        when (didNothing++) {
            0 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = callToHanaCohenInstructionPass to callHanaCohenPass
            )

            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = whatYouNeedToDo to whatDoYouNeedToDoPass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = appsPageSecondAssist to searchContactsListInThePhonePass
            )

            3 -> {
                countdownDialogHandler.showCountdownDialog(
                    isPlayingFlow = isPlaying,
                    audioText = herePersonsNumber to nowTheContactsListWillBeOpenedPass
                )
                _isNextScreen.value = false
                _nextScreen.value = ContactsScreen()
            }
        }
    }


    fun clearNextScreen() {
        _nextScreen.value = null
    }

    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()
        elapsedSeconds = 0
    }

    fun stopAll() {
        println("didNothing: $didNothing")
        println("wrongApp: $wrongApp")
        playAudioUseCase.stopAudio()
        hideReminderDialog()
        reminderJob?.cancel()
        reminderJob = null
    }
}
