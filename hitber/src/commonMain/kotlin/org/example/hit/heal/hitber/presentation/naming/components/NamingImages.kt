package org.example.hit.heal.hitber.presentation.naming.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberPic1
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberPic2
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// Displays two items side by side with styling and spacing.
@Composable
fun NamingImages(selectedCouple: Pair<DrawableResource, DrawableResource>?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        selectedCouple?.let { (firstImage, secondImage) ->
            Image(
                painter = painterResource(firstImage),
                contentDescription = stringResource(fourthQuestionHitberPic1),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(paddingMd)
                    .background(color = Color.White)
            )
            Image(
                painter = painterResource(secondImage),
                contentDescription = stringResource(fourthQuestionHitberPic2),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(paddingMd)
                    .background(color = Color.White)
            )
        }
    }
}
