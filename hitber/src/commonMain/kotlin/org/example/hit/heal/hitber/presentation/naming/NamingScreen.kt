package org.example.hit.heal.hitber.presentation.naming

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import core.utils.getCurrentFormattedDateTime
import getImageName
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberInstructions
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberTitle
import org.example.hit.heal.core.presentation.Resources.String.next
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.components.BaseScreen
import org.example.hit.heal.core.presentation.components.RoundedButton
import org.example.hit.heal.core.presentation.components.ScreenConfig
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.components.InstructionText
import org.example.hit.heal.hitber.presentation.repetition.RepetitionScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * REFACTORED SCREEN to show one image at a time.
 */
class NamingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        // Get both ViewModels
        val fourthQuestionViewModel: FourthQuestionViewModel = koinViewModel()
        val viewModel: ActivityViewModel = koinViewModel()

        // Collect state from the ViewModel
        val answer1 by fourthQuestionViewModel.answer1.collectAsState()
        val answer2 by fourthQuestionViewModel.answer2.collectAsState()
        val selectedCouple by fourthQuestionViewModel.selectedCouple.collectAsState()

        // This logic remains the same, used for saving the answer
        val firstImageName = selectedCouple?.let { getImageName(it.first) } ?: ""
        val secondImageName = selectedCouple?.let { getImageName(it.second) } ?: ""

        // --- NEW STATE LOGIC ---

        // 1. Add local state to track which image we are on (0 = first, 1 = second)
        var currentStep by rememberSaveable { mutableStateOf(0) }
        val isFirstImage = (currentStep == 0)

        // 2. Determine which values to show based on the current step
        val currentResourceToShow = if (isFirstImage) selectedCouple?.first else selectedCouple?.second
        val currentImageName = if (isFirstImage) firstImageName else secondImageName
        val currentAnswer = if (isFirstImage) answer1 else answer2

        // 3. Point to the correct VM update function based on the current step
        val onAnswerChange = if (isFirstImage) {
            { text: String -> fourthQuestionViewModel.onAnswer1Changed(text) }
        } else {
            { text: String -> fourthQuestionViewModel.onAnswer2Changed(text) }
        }

        // 4. Determine button text based on the step
        val buttonText = if (isFirstImage) {
            "Next Image" // Or stringResource(next_image)
        } else {
            stringResource(next)
        }

        // --- UI IMPLEMENTATION ---
        BaseScreen(
            title = stringResource(fourthQuestionHitberTitle),
            config = ScreenConfig.TabletConfig,
            topRightText = "4/10",
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InstructionText(stringResource(fourthQuestionHitberInstructions))
                    Spacer(Modifier.height(paddingMd))

                    OutlinedTextField(
                        value = currentAnswer,
                        onValueChange = onAnswerChange,
                        label = { Text(text = stringResource(next)) },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        singleLine = true
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(vertical = paddingMd),
                        contentAlignment = Alignment.Center
                    ) {
                        if (currentResourceToShow != null) {
                            Image(
                                painter = painterResource(currentResourceToShow),
                                contentDescription = currentImageName, // Good for accessibility
                                modifier = Modifier.fillMaxSize(0.8f),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            // Show a loading indicator while the couple is being set
                            CircularProgressIndicator()
                        }
                    }

                    // 7. Implement two-step button logic
                    RoundedButton(
                        text = buttonText,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(200.dp)
                            .padding(vertical = paddingMd),
                        onClick = {
                            if (isFirstImage) {
                                currentStep = 1
                            } else {
                                fourthQuestionViewModel.fourthQuestionAnswer(
                                    firstImageName,
                                    secondImageName
                                )
                                viewModel.setFourthQuestion(
                                    fourthQuestionViewModel.fourthQuestionAnswers,
                                    getCurrentFormattedDateTime()
                                )
                                navigator?.replace(RepetitionScreen())
                            }
                        }
                    )
                }
            }
        )
        RegisterBackHandler(this) { navigator?.pop() }
    }
}