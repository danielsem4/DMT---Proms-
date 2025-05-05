package presentation.appsDeviceScreen

import BaseTabletScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dmt_proms.pass.generated.resources.Res
import dmt_proms.pass.generated.resources.exit
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.appsDeviceScreen.components.reminderDialog
import presentation.components.AppData

class WrongAppScreen(private val app: AppData) : Screen {

    @Composable
    override fun Content() {
        val viewModel: AppDeviceViewModel = koinViewModel()
        val showReminderDialog by viewModel.showReminderDialog.collectAsState()
        val dialogText by viewModel.dialogText.collectAsState()

        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collect { event ->
                when (event) {
                    is AppDeviceViewModel.NavigationEvent.ToContactsScreen -> {
                        navigator?.popUntilRoot()
                        // אפשרי: ואז push למסך אנשי קשר אם צריך
                    }

                    is AppDeviceViewModel.NavigationEvent.BackToAppsScreen -> {
                        navigator?.pop() // ← מחזיר אחורה למסך הקודם (האפליקציות)
                    }

                    is AppDeviceViewModel.NavigationEvent.ToWrongAppScreen -> {
                        // לא עושים כלום כאן כרגע
                    }
                }
            }
        }

        if (showReminderDialog) {
            reminderDialog(onClick = { viewModel.hideReminderDialog() }, text = dialogText)
        }

        BaseTabletScreen(
            title = "אפליקציה",
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(Res.drawable.exit),
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.TopStart).clickable { viewModel.backToAppsScreen()
                                viewModel.startReminderCountdown()
                            }
                    )
                    Text(
                        text = "האפליקציה שנכנסת אליה היא: ${app.label}",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        )
    }
}


