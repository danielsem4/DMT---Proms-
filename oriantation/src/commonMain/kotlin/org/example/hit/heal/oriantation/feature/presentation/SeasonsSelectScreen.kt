package org.example.hit.heal.oriantation.feature.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dmt_proms.oriantation.generated.resources.Res
import org.example.hit.heal.core.presentation.TabletBaseScreen
import org.jetbrains.compose.resources.painterResource


enum class Season(val displayName: String) {
    WINTER("חורף"),
    SPRING("אביב"),
    SUMMER("קיץ"),
    AUTUMN("סתיו")
}



class SeasonsSelectScreen : Screen {
    @Composable
    override fun Content() {
        var selectedSeason by remember { mutableStateOf(Season.SUMMER) }

        // Map each season to an image resource (replace with your actual images)
        val seasonImages = mapOf(
            Season.WINTER to Res.drawable.,
            Season.SPRING to Res.drawable.spring,
            Season.SUMMER to Res.drawable.summer,
            Season.AUTUMN to Res.drawable.autumn
        )

        // For the "change to" text, cycle to the next season
        val nextSeason = when (selectedSeason) {
            Season.WINTER -> Season.SPRING
            Season.SPRING -> Season.SUMMER
            Season.SUMMER -> Season.AUTUMN
            Season.AUTUMN -> Season.WINTER
        }

        TabletBaseScreen(
            title = "עונות",
            question = 3,
            onNextClick = { /* TODO: Navigate to next screen */ },
            content = {
                Spacer(modifier = Modifier.height(16.dp))

                // Season Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Season.values().forEach { season ->
                        Button(
                            onClick = { selectedSeason = season },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedSeason == season) Color(0xFF4EC3AF) else Color(0xFFB0B0B0)
                            ),
                            shape = RoundedCornerShape(20),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = season.displayName,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Season Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Replace with your actual image loading logic
                    Image(
                        painter = painterResource(seasonImages[selectedSeason] ?: "summer_image"),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Text Cards
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 24.dp, bottom = 8.dp),
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
                                text = "כעת יש לשנות את העונה ל${nextSeason.displayName}",
                                color = Color(0xFF4EC3AF),
                                fontSize = 20.sp
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 24.dp),
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
                                text = "כרגע נמצאים בעונת ${selectedSeason.displayName}",
                                color = Color(0xFF4EC3AF),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        )
    }
}