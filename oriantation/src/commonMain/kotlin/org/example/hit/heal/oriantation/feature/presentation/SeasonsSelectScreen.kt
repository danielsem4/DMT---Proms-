package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import core.utils.RegisterBackHandler
import dmt_proms.oriantation.generated.resources.Res
import dmt_proms.oriantation.generated.resources.Res.string
import dmt_proms.oriantation.generated.resources.autumn
import dmt_proms.oriantation.generated.resources.entry_Oriantation_welcome_note
import dmt_proms.oriantation.generated.resources.oriantation_season_title
import dmt_proms.oriantation.generated.resources.seasons_instructions_app_trial

import dmt_proms.oriantation.generated.resources.spring
import dmt_proms.oriantation.generated.resources.summer
import dmt_proms.oriantation.generated.resources.winter
import org.example.hit.heal.core.presentation.Resources.String.orientationSeasonTitle
import org.example.hit.heal.core.presentation.Resources.String.seasonsInstructionsAppTrial
import org.example.hit.heal.core.presentation.TabletBaseScreen
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
        var selectedSeason by remember { mutableStateOf(Season.WINTER) }



        TabletBaseScreen(
            title = (stringResource(orientationSeasonTitle)),
            question = 3,
            onNextClick = {
                viewModel.updateSeason(selectedSeason.toString())
                navigator?.push(ShapesDragScreen(viewModel)) },
            content = {
                Spacer(modifier = Modifier.height(16.dp))
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                // Main content: Season buttons above the image, cards on the right
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left side: Column with buttons on top, image below
                    Column(
                        modifier = Modifier
                            .weight(1.2f)
                            .height(400.dp) // Even bigger image
                            .padding(end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        // Season Selector (at the very top)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Season.values().forEach { season ->
                                Button(
                                    onClick = { selectedSeason = season },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (selectedSeason == season) Color(0xFF4EC3AF) else Color(0xFFB0B0B0)
                                    ),
                                    shape = RoundedCornerShape(50),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 4.dp)
                                ) {
                                    Text(
                                        text = season.displayName,
                                        color = Color.White,
                                        fontSize = 22.sp // Bigger button text
                                    )
                                }
                            }
                        }
                        // Season Image (bigger)
                        Image(
                            painter = painterResource(
                                when (selectedSeason) {
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

                    // Right side: Text Cards
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            elevation = 4.dp,
                            backgroundColor = Color.White,
                            border = BorderStroke(1.dp, Color(0xFF4EC3AF))
                        ) {
                            Box(
                                modifier = Modifier.padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = (stringResource(seasonsInstructionsAppTrial)),
                                    color = Color(0xFF4EC3AF),
                                    fontSize = 24.sp
                                )
                            }
                        }
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = 4.dp,
                            backgroundColor = Color.White,
                            border = BorderStroke(1.dp, Color(0xFF4EC3AF))
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier.padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "כרגע נמצאים בעונת ${selectedSeason.displayName}",
                                    color = Color(0xFF4EC3AF),
                                    fontSize = 24.sp // Bigger card text
                                )
                            }
                        }
                    }
                }
            }
            }
        )
        RegisterBackHandler(this)
        {
            navigator?.pop()
        }
    }
}









