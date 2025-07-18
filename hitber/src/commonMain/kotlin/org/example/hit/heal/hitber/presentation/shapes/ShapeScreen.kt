package org.example.hit.heal.hitber.presentation.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Resources.Icon.profileIcon
import org.example.hit.heal.core.presentation.Resources.String.`continue`
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberDialogInstructions
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTask
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTitle
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.hitber.presentation.shapes.components.DialogTask
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class ShapeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val secondQuestionViewModel: SecondQuestionViewModel = koinViewModel()
        var showDialog by remember { mutableStateOf(true) }
        val shapeSet by secondQuestionViewModel.selectedSet.collectAsState()

        BaseScreen(
            title = stringResource(secondQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "2/10",
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                    .background(color = Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                )
                {
                    // Display each shape icon from the selected set
                    shapeSet.forEach { shapeRes ->
                        Icon(
                            painter = painterResource(shapeRes.drawable),
                            contentDescription = stringResource(secondQuestionHitberTitle),
                            tint = Color.Unspecified,
                            modifier = Modifier.size(150.dp)
                        )
                    }}

                        RoundedButton(
                            text = stringResource(`continue`),
                            modifier = Modifier.align(Alignment.CenterHorizontally).width(200.dp),
                            onClick = {
                                navigator?.replace(ActionShapesScreen(2))
                            }
                        )

            })

        RegisterBackHandler(this) {
            secondQuestionViewModel.resetAll()
            navigator?.pop()
        }

        // Show dialog with instructions initially
        if (showDialog) {
            DialogTask(
                icon = profileIcon,
                title = stringResource(secondQuestionHitberTask),
                text = stringResource(secondQuestionHitberDialogInstructions),
                onDismiss = { showDialog = false })
        }
    }
}



