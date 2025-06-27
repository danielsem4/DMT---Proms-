package presentation.detailedContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.StringResource
import presentation.components.AppData
import core.domain.use_case.PlayAudioUseCase
import org.example.hit.heal.core.presentation.Resources.Icon.blackMessages
import org.example.hit.heal.core.presentation.Resources.Icon.whitePhone
import org.example.hit.heal.core.presentation.Resources.Icon.whiteVideo
import org.example.hit.heal.core.presentation.Resources.String.contactPageFirstAssist
import org.example.hit.heal.core.presentation.Resources.String.messages
import org.example.hit.heal.core.presentation.Resources.String.phone
import org.example.hit.heal.core.presentation.Resources.String.pressTheNumberOrTheDialButtonPass
import org.example.hit.heal.core.presentation.Resources.String.video
import org.example.hit.heal.core.presentation.Resources.String.whatDoYouNeedToDoPass
import org.example.hit.heal.core.presentation.Resources.String.whatYouNeedToDo
import presentation.components.CountdownDialogHandler
import presentation.nextQuestion.NextQuestionScreen

class DetailedContactViewModel(private val countdownDialogHandler: CountdownDialogHandler,
                               private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

    val items = listOf(
        AppData(
            imageRes = whitePhone,
            circleColor = primaryColor,
            label = phone,
        ),
        AppData(
            imageRes = blackMessages,
            circleColor = primaryColor,
            label = messages,
        ),
        AppData(
            imageRes = whiteVideo,
            circleColor = primaryColor,
            label = video,
        )
    )

    val dialogAudioText = countdownDialogHandler.dialogAudioText
    val showDialog = countdownDialogHandler.showDialog
    val countdown = countdownDialogHandler.countdown
    val isCountdownActive = countdownDialogHandler.isCountdownActive

    val isPlaying = playAudioUseCase.isPlaying

    private var didNothing = 0
    private var wrongClick = 0

    private var reminderJob: Job? = null
    private var elapsedSeconds = 0

    private val _nextScreen = MutableStateFlow<Screen?>(null)
    val nextScreen = _nextScreen.asStateFlow()

    private val _isNextScreen = MutableStateFlow(false)
    val isNextScreen: StateFlow<Boolean> = _isNextScreen

    fun startCheckingIfUserDidSomething() {

        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            while (isActive && didNothing + wrongClick <= 3) {

                delay(1_000)

                if (showDialog.value) {
                    continue
                }

                elapsedSeconds++

                if (elapsedSeconds >= 15) {
                    didNothing++
                    getReminderText()
                    elapsedSeconds = 0
                }

            }
        }
    }

    fun onUserClicked(item: StringResource) {

        if (item == phone) {
            nextQuestion()
            return
        }

        wrongClick++
        getReminderText()
    }

    private fun getReminderText() {
        val count = didNothing + wrongClick
        return when (count) {
            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = whatYouNeedToDo to whatDoYouNeedToDoPass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = contactPageFirstAssist to pressTheNumberOrTheDialButtonPass
            )

            else -> {
                nextQuestion()
            }
        }
    }

    fun onPlayAudioRequested(audioText: String) {
        playAudioUseCase.playAudio(audioText)
    }

    fun hideReminderDialog() {
        countdownDialogHandler.hideDialog()
        elapsedSeconds = 0
    }


    fun clearNextScreen() {
        _nextScreen.value = null
    }

    private fun nextQuestion(){
        reminderJob?.cancel()
        _isNextScreen.value = true
        _nextScreen.value = NextQuestionScreen()
    }
}