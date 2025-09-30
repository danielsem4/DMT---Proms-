package com.example.new_memory_test.presentation.screens.MemoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.components.BulletPointText
import com.example.new_memory_test.presentation.screens.RoomInsructionsScreen.RoomInstructionsScreen
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Resources.String.start
import org.example.hit.heal.core.presentation.Sizes.heightSm
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.Sizes.spacingXl
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.backgroundColor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 *
 */

class MemoryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ViewModelMemoryTest = koinViewModel()

        viewModel.txtMemoryPage = 1

        LaunchedEffect(Unit) {
            viewModel.loadEvaluation("Memory")
        }

        //Save in one side and don't depend on language
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            TabletBaseScreen(
                title = stringResource(Resources.String.memory_title),
                onNextClick = {
                    viewModel.setPage(viewModel.txtMemoryPage + 1)
                    navigator.replace(RoomInstructionsScreen(pageNumber = 1))
                },
                question = 1,
                buttonText = stringResource(start),
                content = {
                    BoxWithConstraints {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = backgroundColor),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Spacer(modifier = Modifier.height(spacingXl))
                            Text(
                                text = stringResource(Resources.String.instructions),
                                fontSize = LARGE,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(spacingSm))
                            BulletPointText(text = stringResource(Resources.String.task_duration))
                            BulletPointText(text = stringResource(Resources.String.task_continuity))
                            BulletPointText(text = stringResource(Resources.String.read_instructions))
                            BulletPointText(text = stringResource(Resources.String.complete_all_tasks))
                            BulletPointText(text = stringResource(Resources.String.quiet_room_tip))
                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = stringResource(Resources.String.good_luck),
                                fontSize = EXTRA_MEDIUM,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(spacingXl))

                            Text(
                                text = stringResource(Resources.String.press_start_to_begin),
                                fontSize = EXTRA_MEDIUM,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(spacingMd))

                            Spacer(modifier = Modifier.height(heightSm))
                        }
                    }


                })
        }
        RegisterBackHandler(this)
        {
            viewModel.reset()
            navigator.popUntilRoot()
        }
    }

}