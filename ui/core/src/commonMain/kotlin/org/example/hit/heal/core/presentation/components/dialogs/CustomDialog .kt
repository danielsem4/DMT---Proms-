package org.example.hit.heal.core.presentation.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.pill
import org.example.hit.heal.core.presentation.FontSize.EXTRA_MEDIUM_LARGE
import org.example.hit.heal.core.presentation.FontSize.LARGE
import org.example.hit.heal.core.presentation.FontSize.REGULAR
import org.example.hit.heal.core.presentation.Sizes.heightSm
import org.example.hit.heal.core.presentation.Sizes.iconSize2Xl
import org.example.hit.heal.core.presentation.Sizes.radiusMd2
import org.example.hit.heal.core.presentation.Sizes.radiusXl
import org.example.hit.heal.core.presentation.Sizes.spacingLg
import org.example.hit.heal.core.presentation.Sizes.spacingSm
import org.example.hit.heal.core.presentation.Sizes.spacingXl
import org.example.hit.heal.core.presentation.Sizes.spacingXxl
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 *
 */

@Composable
fun CustomDialog(
    icon: DrawableResource,
    title: String,
    text: String? = "",
    description: String? = "",
    onSubmit: (() -> Unit)? = null,
    onDismiss: () -> Unit,
    buttons: List<Pair<String, () -> Unit>> = emptyList(),
    content: (@Composable (() -> Unit))? = null
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = true
        )
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .padding(horizontal = spacingXl)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            // Card Body
            Box(
                modifier = Modifier
                    .padding(heightSm)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(radiusXl))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        top = spacingXxl,
                        start = spacingLg,
                        end = spacingLg,
                        bottom = spacingLg
                    )
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = LARGE,
                        color = primaryColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(spacingSm))

                    if (description != null) {
                        Text(
                            text = description,
                            textAlign = TextAlign.Center,
                            color = primaryColor,
                            fontSize = EXTRA_MEDIUM_LARGE
                        )
                    }

                    if (content != null) {
                        Spacer(modifier = Modifier.height(spacingLg))
                        content()
                    }

                    Spacer(modifier = Modifier.height(spacingLg))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(radiusMd2),
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        buttons.forEach { (text, action) ->
                            Button(
                                onClick = action,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = primaryColor,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(radiusMd2),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = text,
                                    fontSize = REGULAR,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            // Icon
            Box(
                modifier = Modifier
                    .size(iconSize2Xl)
                    .zIndex(1f)
                    .clip(CircleShape)
                    .background(primaryColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialog() {

    CustomDialog(
        icon = Res.drawable.pill,
        title = "Dialog Title",
        description = "This is a sample description for the custom dialog fjsdfi fsaidfjisa fsdif jisdf sidjf isadf jsfij asdf.",
        onDismiss = {},
        buttons = listOf(
            "OK" to {}
        )
    )
}
