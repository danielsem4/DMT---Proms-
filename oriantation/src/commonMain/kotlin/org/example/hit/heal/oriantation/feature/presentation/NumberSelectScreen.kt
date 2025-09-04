package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Black
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM_LARGE
import org.example.hit.heal.core.presentation.Resources.String.secQuestionInstruction
import org.example.hit.heal.core.presentation.Resources.String.secQuestionNumber
import org.example.hit.heal.core.presentation.Resources.String.secQuestionTitle
import org.example.hit.heal.core.presentation.Sizes.elevationMd
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.radiusMd2
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.core.presentation.components.DropDownItem
import org.example.hit.heal.core.presentation.components.DropDownQuestionField
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.stringResource

/**
 * Screen where user can select a number from 0 to 20 using the shared M3 DropDownQuestionField.
 */

class NumberSelectScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        var selectedText by remember {
            mutableStateOf("")
        }

        val selectedNumber = selectedText.toIntOrNull() ?: 0

        TabletBaseScreen(
            title = stringResource(secQuestionTitle),
            question = 2,
            onNextClick = { navigator?.push(SeasonsSelectScreen(viewModel)) },
            content = {
                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingLg),
                    shape = RoundedCornerShape(radiusMd2),
                    elevation = elevationMd,
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, primaryColor)
                ) {
                    Box(
                        modifier = Modifier.padding(paddingMd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(secQuestionInstruction),
                            color = primaryColor,
                            fontSize = EXTRA_MEDIUM_LARGE
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingLg)
                        .background(Black)
                ) {
                    val items = remember { (0..20).map { DropDownItem(it.toString()) } }

                    DropDownQuestionField(
                        question = stringResource(secQuestionNumber),
                        dropDownItems = items,
                        selectedText = selectedText,
                        onItemClick = { chosen ->
                            selectedText = chosen.text
                            viewModel.updateNumber(chosen.text.toInt())
                        }
                    )
                }

                Spacer(modifier = Modifier.height(150.dp))

                // Selected Number Card
                Card(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = paddingLg),
                    shape = RoundedCornerShape(radiusMd2),
                    elevation = elevationMd,
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, primaryColor)
                ) {
                    Box(
                        modifier = Modifier.padding(paddingMd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${stringResource(secQuestionNumber)}, $selectedNumber",
                            color = primaryColor,
                            fontSize = EXTRA_MEDIUM_LARGE
                        )
                    }
                }
            }
        )

        RegisterBackHandler(this) {
            navigator?.popUntilRoot()
        }
    }
}
