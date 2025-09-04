package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.autumn
import dmt_proms.oriantation.generated.resources.spring
import dmt_proms.oriantation.generated.resources.summer
import dmt_proms.oriantation.generated.resources.winter
import org.example.hit.heal.core.presentation.FontSize
import org.example.hit.heal.core.presentation.Sizes
import org.example.hit.heal.core.presentation.Resources.String.YouInTheSeasons
import org.example.hit.heal.core.presentation.Resources.String.orientationSeasonTitle
import org.example.hit.heal.core.presentation.Resources.String.seasonsInstructionsAppTrial
import org.example.hit.heal.core.presentation.Resources.String.seasonsInstructionsAppTrial2
import org.example.hit.heal.core.presentation.Resources.String.youInTheAutumn
import org.example.hit.heal.core.presentation.Resources.String.youInTheSpring
import org.example.hit.heal.core.presentation.Resources.String.youInTheSummer
import org.example.hit.heal.core.presentation.Resources.String.youInTheWinter
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.example.hit.heal.core.presentation.primaryColor
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

enum class Season(val displayName: String) {
    WINTER("חורף"),
    SPRING("אביב"),
    SUMMER("קיץ"),
    AUTUMN("סתיו")
}

class SeasonsSelectScreen(
    private val viewModel: OrientationTestViewModel
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        var phase by remember { mutableStateOf(1) }
        var selectedSeason1 by remember { mutableStateOf<Season?>(Season.SPRING) }
        var selectedSeason2 by remember { mutableStateOf<Season?>(null) }
        val currentSelected = if (phase == 1) selectedSeason1 else selectedSeason2

        val titleText = stringResource(orientationSeasonTitle)
        val instructionText = if (phase == 1)
            stringResource(seasonsInstructionsAppTrial)
        else
            stringResource(seasonsInstructionsAppTrial2)

        @Composable
        fun youInSeasonLine(season: Season?): String = when (season) {
            Season.WINTER -> stringResource(youInTheWinter)
            Season.SPRING -> stringResource(youInTheSpring)
            Season.SUMMER -> stringResource(youInTheSummer)
            Season.AUTUMN -> stringResource(youInTheAutumn)
            null -> ""
        }

        val canProceed = currentSelected != null

        TabletBaseScreen(
            title = titleText,
            question = 3,
            onNextClick = {
                if (!canProceed) return@TabletBaseScreen
                if (phase == 1) {
                    // keep the selection in phase 2 the same as phase 1
                    selectedSeason2 = selectedSeason1
                    phase = 2
                } else {
                    val s1 = (selectedSeason1 ?: Season.WINTER).name
                    val s2 = (selectedSeason2 ?: selectedSeason1 ?: Season.WINTER).name
                    viewModel.updateSeasons(listOf(s1, s2))
                    navigator?.push(ShapesDragScreen(viewModel))
                }
            },
            content = {
                Spacer(modifier = Modifier.height(Sizes.spacingMd))
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Sizes.spacingLg),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left: season buttons + image
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(end = Sizes.spacingMd),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Selector
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = Sizes.spacingSm),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Season.values().forEach { season ->
                                    Button(
                                        onClick = {
                                            if (phase == 1) {
                                                selectedSeason1 = season
                                            } else {
                                                selectedSeason2 = season
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor =
                                                if (currentSelected == season) primaryColor
                                                else Color(0xFFB0B0B0)
                                        ),
                                        modifier = Modifier
                                            .weight(1.5f)
                                            .padding(horizontal = Sizes.spacingXs)
                                            .height(Sizes.buttonHeightMd)
                                    ) {
                                        Text(
                                            text = season.displayName,
                                            color = Color.White,
                                            fontSize = FontSize.LARGE
                                        )
                                    }
                                }
                            }

                            // Image bound to current selection
                            val imgSeason = currentSelected ?: Season.WINTER
                            Image(
                                painter = painterResource(
                                    when (imgSeason) {
                                        Season.WINTER -> Res.drawable.winter
                                        Season.SPRING -> Res.drawable.spring
                                        Season.SUMMER -> Res.drawable.summer
                                        Season.AUTUMN -> Res.drawable.autumn
                                    }
                                ),
                                contentDescription = "selectedSeason",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        // Right: instruction + “you in the seasons”
                        Column(
                            modifier = Modifier
                                .weight(0.6f)
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Instructions card
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = Sizes.spacingSm),
                                shape = RoundedCornerShape(Sizes.radiusMd2),
                                elevation = Sizes.elevationMd,
                                backgroundColor = Color.White,
                                border = BorderStroke(1.dp, primaryColor)
                            ) {
                                Box(
                                    modifier = Modifier.padding(Sizes.paddingMd),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = instructionText,
                                        color = primaryColor,
                                        fontSize = FontSize.EXTRA_MEDIUM_LARGE
                                    )
                                }
                            }

                            // “You in the seasons” card
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(Sizes.radiusMd2),
                                elevation = Sizes.elevationMd,
                                backgroundColor = Color.White,
                                border = BorderStroke(1.dp, primaryColor)
                            ) {
                                Spacer(modifier = Modifier.height(Sizes.spacingMd))
                                Box(
                                    modifier = Modifier.padding(Sizes.paddingMd),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val header = stringResource(YouInTheSeasons)
                                    val line = youInSeasonLine(currentSelected)
                                    val label = if (line.isNotEmpty()) line else header
                                    Text(
                                        text = label,
                                        color = primaryColor,
                                        fontSize = FontSize.LARGE
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )

        RegisterBackHandler(this) {
            if (phase == 2) {
                phase = 1
                // keep phase 1 selection as-is; user can change again
            } else {
                navigator?.popUntilRoot()
            }
        }
    }
}
