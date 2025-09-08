package org.example.hit.heal.cdt.presentation.grade

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.utils.RegisterBackHandler
import org.example.hit.heal.core.presentation.Sizes.paddingLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.components.DropDownItem
import org.example.hit.heal.core.presentation.components.DropDownQuestionField
import org.jetbrains.compose.resources.stringResource
import org.example.hit.heal.cdt.presentation.ClockTestViewModel
import org.example.hit.heal.core.presentation.Resources.String.circleCompletenessTitle
import org.example.hit.heal.core.presentation.Resources.String.circleGrades
import org.example.hit.heal.core.presentation.Resources.String.clock
import org.example.hit.heal.core.presentation.Resources.String.handsGrades
import org.example.hit.heal.core.presentation.Resources.String.handsPositionTitle
import org.example.hit.heal.core.presentation.Resources.String.numbersGrades
import org.example.hit.heal.core.presentation.Resources.String.numbersPresenceTitle
import org.jetbrains.compose.resources.getStringArray
import org.jetbrains.compose.resources.stringArrayResource
import org.koin.compose.viewmodel.koinViewModel


/**
 * Screen showing the test drawing and 3 grading dropdowns.
 * Left: the saved clock image from ViewModel
 * Right: three dropdowns using the shared component.
 */

class ClockGrade() : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<ClockTestViewModel>()

        val savedBitmap: ImageBitmap? by viewModel.savedClockDrawing.collectAsState()

        val circlePerfection by viewModel.circlePerfection.collectAsState()
        val numbersSequence by viewModel.numbersSequence.collectAsState()
        val handsPosition by viewModel.handsPosition.collectAsState()

        val structureItems = stringArrayResource(circleGrades).map(::DropDownItem)
        val digitsItems    = stringArrayResource(numbersGrades).map(::DropDownItem)
        val placementItems = stringArrayResource(handsGrades).map(::DropDownItem)


        TabletBaseScreen(
            title = stringResource(clock),
            question = 7,
            onNextClick = {
                // collect your selections here if you need to push them into the VM
                navigator.pop()
            },
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = paddingLg, vertical = paddingMd),
                    horizontalArrangement = Arrangement.spacedBy(paddingLg),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ---------- LEFT: the image ----------
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (savedBitmap != null) {
                            Image(
                                bitmap = savedBitmap!!,
                                contentDescription = "Clock drawing",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f) // keep it nicely square
                                    .padding(paddingMd)
                            )
                        } else {
                            Text(
                                "No drawing available",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                modifier = Modifier.padding(paddingSm)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DropDownQuestionField(
                            question = stringResource(circleCompletenessTitle),
                            dropDownItems = structureItems,
                            selectedText = circlePerfection,
                            onItemClick = { viewModel.setCirclePerfection(it.text) }
                        )
                        DropDownQuestionField(
                            question = stringResource(numbersPresenceTitle),
                            dropDownItems = digitsItems,
                            selectedText = numbersSequence,
                            onItemClick = { viewModel.setNumbersSequence(it.text) }
                        )
                        DropDownQuestionField(
                            question = stringResource(handsPositionTitle),
                            dropDownItems = placementItems,
                            selectedText = handsPosition,
                            onItemClick = { viewModel.setHandsPosition(it.text) }
                        )
                    }
                }
            }
        )
        RegisterBackHandler(this) {
            navigator.pop()
        }
    }
}
