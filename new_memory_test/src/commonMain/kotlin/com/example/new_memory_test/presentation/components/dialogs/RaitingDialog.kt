package com.example.new_memory_test.presentation.components.dialogs
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM_LARGE
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes.heightMd_Lg
import org.example.hit.heal.core.presentation.Sizes.heightSm
import org.example.hit.heal.core.presentation.Sizes.iconSizeLg
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.radiusLg
import org.example.hit.heal.core.presentation.Sizes.spacing4Xl
import org.example.hit.heal.core.presentation.Sizes.spacingLg
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource




@Composable
fun RatingDialog(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {

    // Set the layout direction to RTL (Right-to-Left)
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr){
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(radiusLg))
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightMd_Lg)
                        .background(
                            color = primaryColor,
                            shape = RoundedCornerShape(topStart = spacingMd, topEnd = spacingMd)
                        ),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightMd_Lg)
                            .background(
                                color = primaryColor,
                                shape = RoundedCornerShape(topStart = spacingMd, topEnd =spacingMd)
                            ),
                        contentAlignment = Alignment.BottomCenter
                    ) {

                        Box(
                            modifier = Modifier
                                .offset(y = spacingLg)
                                .size(spacing4Xl)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = rating.toString(),
                                fontSize = EXTRA_MEDIUM_LARGE,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(heightSm))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingMd)
                ) {
                    Text(
                        text = stringResource(Resources.String.place_rate_memory),
                        fontSize = EXTRA_MEDIUM,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(Resources.String.how_well_do_you_feel_memory),
                        fontSize = EXTRA_MEDIUM,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(paddingMd))

                    //Icons of starts
                    Row(horizontalArrangement = Arrangement.Center) {
                        for (i in 1..5) {
                            val icon = when {
                                rating >= i -> Icons.Filled.Star
                                rating >= i - 0.5f -> Icons.Filled.StarHalf
                                else -> Icons.Outlined.Star
                            }
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(iconSizeLg)
                                    .clickable {
                                        val newRating =
                                            if (rating == i.toFloat()) i - 0.5f else i.toFloat()
                                        onRatingChanged(newRating.coerceIn(0f, 5f))
                                    },
                                tint = if (rating >= i - 0.5f) primaryColor else Color.LightGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(spacingLg))
                    Button(
                        onClick = {
                            onSubmit()
                        },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(heightSm)
                    ) {
                        Text(
                            text = stringResource(Resources.String.rate),
                            fontSize = EXTRA_MEDIUM,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(spacingMd))
                }
            }
        }

        }
    }
}

