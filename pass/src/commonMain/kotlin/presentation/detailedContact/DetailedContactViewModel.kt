package presentation.detailedContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.black_messages
import dmt_proms.pass.generated.resources.calculator
import dmt_proms.pass.generated.resources.camera
import dmt_proms.pass.generated.resources.clock
import dmt_proms.pass.generated.resources.contact_page_first_assist
import dmt_proms.pass.generated.resources.contacts
import dmt_proms.pass.generated.resources.contacts_page_first_assist
import dmt_proms.pass.generated.resources.contacts_page_second_assist
import dmt_proms.pass.generated.resources.contacts_page_thired_assist
import dmt_proms.pass.generated.resources.documents
import dmt_proms.pass.generated.resources.email
import dmt_proms.pass.generated.resources.messages
import dmt_proms.pass.generated.resources.my_files
import dmt_proms.pass.generated.resources.phone
import dmt_proms.pass.generated.resources.press_the_number_or_the_dial_button_pass
import dmt_proms.pass.generated.resources.purse
import dmt_proms.pass.generated.resources.search_at_latter_h_pass
import dmt_proms.pass.generated.resources.search_for_hana_choen_in_contacts_pass
import dmt_proms.pass.generated.resources.settings
import dmt_proms.pass.generated.resources.store
import dmt_proms.pass.generated.resources.video
import dmt_proms.pass.generated.resources.weather
import dmt_proms.pass.generated.resources.what_do_you_need_to_do_pass
import dmt_proms.pass.generated.resources.what_you_need_to_do
import dmt_proms.pass.generated.resources.white_messages
import dmt_proms.pass.generated.resources.white_phone
import dmt_proms.pass.generated.resources.white_video
import dmt_proms.pass.generated.resources.witch_contact_are_we_looking_for_pass
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import org.example.hit.heal.core.presentation.Colors
import org.example.hit.heal.core.presentation.Colors.primaryColor
import org.jetbrains.compose.resources.StringResource
import presentation.components.AppData
import presentation.components.AudioPlayer
import presentation.components.ContactData
import presentation.components.CountdownDialogHandler
import presentation.components.CountdownTimerUseCase
import presentation.components.PlayAudioUseCase
import presentation.dialScreen.DialScreen
import presentation.nextQuestion.NextQuestionScreen

class DetailedContactViewModel(private val countdownDialogHandler: CountdownDialogHandler,
                               private val playAudioUseCase: PlayAudioUseCase
) : ViewModel() {

    val items = listOf(
        AppData(
            imageRes = Res.drawable.white_phone,
            circleColor = primaryColor,
            label = Res.string.phone,
        ),
        AppData(
            imageRes = Res.drawable.black_messages,
            circleColor = primaryColor,
            label = Res.string.messages,
        ),
        AppData(
            imageRes = Res.drawable.white_video,
            circleColor = primaryColor,
            label = Res.string.video,
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

    private val _nextScreen = MutableStateFlow<Screen?>(null)
    val nextScreen = _nextScreen.asStateFlow()

    private val _isNextScreen = MutableStateFlow(false)
    val isNextScreen: StateFlow<Boolean> = _isNextScreen

    fun startCheckingIfUserDidSomething() {

        reminderJob?.cancel()

        reminderJob = viewModelScope.launch {
            var elapsedTime = 0

            while (isActive && didNothing + wrongClick <= 3) {

                delay(1_000)

                if (showDialog.value) {
                    continue
                }

                elapsedTime++

                if (elapsedTime >= 15) {
                    didNothing++
                    getReminderText()
                    elapsedTime = 0
                }

            }
        }
    }

    fun onUserClicked(item: StringResource) {

        if (item == Res.string.phone) {
            nextQuestion()
            return
        }

        startCheckingIfUserDidSomething()
        wrongClick++
        getReminderText()
    }

    private fun getReminderText() {
        val count = didNothing + wrongClick
        return when (count) {
            1 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = Res.string.what_you_need_to_do to Res.string.what_do_you_need_to_do_pass
            )

            2 -> countdownDialogHandler.showCountdownDialog(
                isPlayingFlow = isPlaying,
                audioText = Res.string.contact_page_first_assist to Res.string.press_the_number_or_the_dial_button_pass
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
        startCheckingIfUserDidSomething()
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