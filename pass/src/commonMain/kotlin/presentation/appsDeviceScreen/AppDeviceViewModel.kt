package presentation.appsDeviceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import presentation.components.CountdownDialogHandler
import presentation.components.AppData
import core.domain.use_case.PlayAudioUseCase
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
import org.example.hit.heal.core.presentation.calculatorColor
import org.example.hit.heal.core.presentation.cameraColor
import org.example.hit.heal.core.presentation.clockColor
import org.example.hit.heal.core.presentation.contactsColor
import org.example.hit.heal.core.presentation.documentsColor
import org.example.hit.heal.core.presentation.emailColor
import org.example.hit.heal.core.presentation.messagesColor
import org.example.hit.heal.core.presentation.phoneColor
import org.example.hit.heal.core.presentation.purseColor
import org.example.hit.heal.core.presentation.settingsColor
import org.example.hit.heal.core.presentation.storeColor
import org.example.hit.heal.core.presentation.weatherColor
import presentation.appsDeviceScreen.AppDeviceProgressCache.didNothing
import presentation.appsDeviceScreen.AppDeviceProgressCache.isSecondInstructions
import presentation.appsDeviceScreen.AppDeviceProgressCache.resetAppDeviceProgress
import presentation.appsDeviceScreen.AppDeviceProgressCache.wrongApp
import presentation.appsDeviceScreen.AppProgressCache.resetAppProgress
import presentation.contatcts.ContactsScreen

class AppDeviceViewModel(
    private val countdownDialogHandler: CountdownDialogHandler,
    private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

    val items = listOf(
        AppData(
            imageRes = calculatorIcon,
            circleColor = calculatorColor,
            label = calculator
        ),
        AppData(
            imageRes = settingsIcon,
            circleColor = settingsColor,
            label = settings
        ),
        AppData(
            imageRes = cameraIcon,
            circleColor = cameraColor,
            label = camera
        ),
        AppData(
            imageRes = emailIcon,
            circleColor = emailColor,
            label = email
        ),
        AppData(
            imageRes = storeIcon,
            circleColor = storeColor,
            label = store
        ),
        AppData(
            imageRes = clockIcon,
            circleColor = clockColor,
            label = clock
        ),
        AppData(
            imageRes = contactsIcon,
            circleColor = contactsColor,
            label = contacts
        ),
        AppData(
            imageRes = whiteMessages,
            circleColor = messagesColor,
            label = messages
        ),
        AppData(
            imageRes = purseIcon,
            circleColor = purseColor,
            label = purse
        ),
        AppData(
            imageRes = weatherIcon,
            circleColor = weatherColor,
            label = weather
        ),
        AppData(
            imageRes = documentsIcon,
            circleColor = documentsColor,
            label = myFiles
        ),
        AppData(
            imageRes = whitePhone,
            circleColor = phoneColor,
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

    private var reminderJob: Job? = null

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
        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            if (didNothing <= 3) {

                delay(15_000)
                getReminderDidNotingText()
                _isCloseIconDialog.value = true

            }
        }
    }

    fun onPlayAudioRequested(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }

    fun onUnderstandingConfirmed() {
        _showUnderstandingDialog.value = false
        startCheckingIfUserDidSomething()
    }

    fun onUnderstandingDenied() {
        _showUnderstandingDialog.value = false
        isSecondInstructions = true
        didNothing--
        startDialogInstructions()
        reminderJob?.cancel()
    }

    fun onUnderstandingDidNothing(){
        _showUnderstandingDialog.value = false
    }

    fun onAppClicked(app: AppData) {
        if (app.label == contacts) {
            _nextScreen.value = ContactsScreen()
            resetAll()
            return
        }
        wrongApp++

        _nextScreen.value = if (wrongApp == 3) {
            resetAll()

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
                resetAll()
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
        startCheckingIfUserDidSomething()
    }

    fun stopAll() {
        println("didNothing: $didNothing")
        println("wrongApp: $wrongApp")
        playAudioUseCase.stopAudio()
        countdownDialogHandler.hideDialog()
        reminderJob?.cancel()
        reminderJob = null
    }

    private fun resetAll(){
        resetAppProgress()
        resetAppDeviceProgress()
    }

}
