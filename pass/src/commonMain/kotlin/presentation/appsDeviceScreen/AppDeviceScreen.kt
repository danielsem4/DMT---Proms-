package presentation.appsDeviceScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.ObserveLifecycle
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources.Icon.likeIcon
import org.example.hit.heal.core.presentation.Resources.String.deviceAppsTitle
import org.example.hit.heal.core.presentation.Resources.String.no
import org.example.hit.heal.core.presentation.Resources.String.understandingDialogText
import org.example.hit.heal.core.presentation.Resources.String.yes
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.BaseYesNoDialog
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.components.InstructionsDialog
import presentation.components.AppData
import presentation.components.circleWithPicture
import presentation.contatcts.ContactsScreen

class AppDeviceScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: AppDeviceViewModel = koinViewModel()
        val wrongAppViewModel: WrongAppViewModel = koinViewModel()
        val navigator = LocalNavigator.current

        val items: List<AppData> = viewModel.items.map { item ->
            AppData(
                imageRes = item.imageRes,
                circleColor = item.circleColor,
                label = item.label
            )
        }

        val isPlaying by viewModel.isPlaying.collectAsState()
        val isNextScreen by viewModel.isNextScreen.collectAsState()
        val isCloseIconDialog by viewModel.isCloseIconDialog.collectAsState()
        val showUnderstandingDialog by viewModel.showUnderstandingDialog.collectAsState()
        val countdown by viewModel.countdown.collectAsState()
        val showDialog by viewModel.showDialog.collectAsState()
        val dialogAudioText by viewModel.dialogAudioText.collectAsState()
        val isCountdownActive by viewModel.isCountdownActive.collectAsState()
        val nextScreen by viewModel.nextScreen.collectAsState()

        BaseScreen(
            title = stringResource(deviceAppsTitle),
            config = ScreenConfig.TabletConfig,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingMd)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(paddingSm),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        itemsIndexed(items) { _, item ->
                            circleWithPicture(item = item) {
                                viewModel.onAppClicked(item)
                            }
                        }
                    }
                }
            }
        )


        LaunchedEffect(nextScreen) {
            if (isNextScreen) {
                nextScreen?.let { screen ->
                    if(nextScreen == ContactsScreen()){
                        viewModel.resetAppDeviceProgress()
                        wrongAppViewModel.resetAppProgress()
                    }
                    navigator?.push (screen)
                    viewModel.clearNextScreen()
                }
            }
        }

        ObserveLifecycle(
            onStop = {
                viewModel.stopAll()
            },
            onStart = {
                viewModel.startCheckingIfUserDidSomething()
            }
        )
        DisposableEffect(Unit) {
            onDispose {
                viewModel.stopAll()
            }
        }

        if (showUnderstandingDialog) {
            BaseYesNoDialog(
                onDismissRequest = {  },
                title = stringResource(understandingDialogText),
                icon = likeIcon,
                message = "",
                confirmButtonText = stringResource(yes),
                confirmButtonColor = primaryColor,
                onConfirm = { viewModel.onUnderstandingConfirmed() },
                dismissButtonText = stringResource(no),
                dismissButtonColor = primaryColor,
                onDismissButtonClick = { viewModel.onUnderstandingDenied() }
            )
        }



        if (showDialog) {
            dialogAudioText?.let { (text, audio) ->
                val audioString = stringResource(audio)
                InstructionsDialog(
                    text = stringResource(text),
                    secondsLeft = countdown,
                    isPlaying = isPlaying,
                    shouldShowCloseIcon = isCloseIconDialog,
                    isCountdownActive = isCountdownActive,
                    onPlayAudio = { viewModel.onPlayAudioRequested(audioString) },
                    onDismiss = {
                        viewModel.hideReminderDialog()

                        nextScreen?.let { screen ->
                            if(nextScreen == ContactsScreen()){
                                viewModel.resetAppDeviceProgress()
                                wrongAppViewModel.resetAppProgress()
                            }
                            navigator?.push (screen)
                            viewModel.clearNextScreen()
                        }
                    }
                )
            }
        }

        RegisterBackHandler(this) {
            viewModel.resetAppDeviceProgress()
            wrongAppViewModel.resetAppProgress()
            navigator?.popUntilRoot()
        }
    }
}
