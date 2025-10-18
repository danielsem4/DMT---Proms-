//shows the right recipe image (salad, pie, or cake) with rounded corners.

package com.example.finalprojectnew.presentation.stage1.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource

import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.cake
import finalprojectnew.composeapp.generated.resources.pie
import finalprojectnew.composeapp.generated.resources.salad

@Composable
fun RecipeImageCompose(
    recipeName: String,
    modifier: Modifier = Modifier
) {
    val drawable = when (recipeName) {
        "pie"   -> Res.drawable.pie
        "salad" -> Res.drawable.salad
        "cake"  -> Res.drawable.cake
        else    -> Res.drawable.pie
    }

    Image(
        painter = painterResource(drawable),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(15))
    )
}