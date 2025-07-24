package core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import androidx.activity.compose.BackHandler
import org.example.hit.heal.core.presentation.Resources.Icon.errorIcon
import org.example.hit.heal.core.presentation.Resources.String.areYouSure
import org.example.hit.heal.core.presentation.Resources.String.backConfirmationMessage
import org.example.hit.heal.core.presentation.Resources.String.no
import org.example.hit.heal.core.presentation.Resources.String.yes
import org.example.hit.heal.core.presentation.components.dialogs.BaseYesNoDialog
import org.jetbrains.compose.resources.stringResource

@Composable
actual fun RegisterBackHandler(screen: Screen, onPop: () -> Unit) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        BaseYesNoDialog(
                onDismissRequest = { showDialog.value = false },
                icon = errorIcon,
                title = stringResource(areYouSure),
                message = stringResource(backConfirmationMessage),
                confirmButtonText = stringResource(yes),
                onConfirm = {
                    showDialog.value = false
                    onPop()
                },
                dismissButtonText = stringResource(no),
                onDismissButtonClick = { showDialog.value = false }
            )
    }

    if (screen is BackHandler) {
        BackHandler {
            val handled = screen.onBackPressed()
            if (!handled) {
                showDialog.value = true
            }
        }
    } else {
        BackHandler {
            showDialog.value = true
        }
    }
}
