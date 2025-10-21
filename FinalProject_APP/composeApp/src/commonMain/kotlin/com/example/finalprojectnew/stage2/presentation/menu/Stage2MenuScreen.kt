package com.example.finalprojectnew.stage2.presentation.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.stage2_baggage
import finalprojectnew.composeapp.generated.resources.stage2_cart
import finalprojectnew.composeapp.generated.resources.stage2_magnifying_glass
import finalprojectnew.composeapp.generated.resources.ic_list
import finalprojectnew.composeapp.generated.resources.logo_super_easy
import finalprojectnew.composeapp.generated.resources.stage2_donation
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.ui.graphics.SolidColor
import com.example.finalprojectnew.stage2.presentation.Stage2Colors

@Composable
fun Stage2MenuScreen(
    onCategories: () -> Unit,
    onCart: () -> Unit,
    onSearch: () -> Unit,
    onViewList: () -> Unit,
    onViewDonationList: () -> Unit,
) {
    val screenBg = Stage2Colors.ScreenBg
    val brandGreen = Stage2Colors.BrandGreen
    val frameGreen = Stage2Colors.FrameGreen
    val tileWhite = Stage2Colors.TileWhite
    val bodyText = Stage2Colors.BodyText

    CompositionLocalProvider(androidx.compose.ui.platform.LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface( // background screen
            color = screenBg,
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.safeDrawing.asPaddingValues())
        ) {
            BoxWithConstraints(Modifier.fillMaxSize()) {
                val w = maxWidth.value
                val scale = when {
                    w >= 1000f -> 1.35f
                    w >= 800f -> 1.22f
                    w >= 600f -> 1.10f
                    else -> 1.00f
                }

                Box(Modifier.fillMaxSize()) {
                    // top area
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 12.dp * scale, start = 24.dp * scale),
                        verticalArrangement = Arrangement.spacedBy(10.dp * scale)
                    ) {
                        ListPillButtonMint(
                            textLine1 = "לצפייה",
                            textLine2 = "ברשימה",
                            onClick = onViewList,
                            icon = painterResource(Res.drawable.ic_list),
                            green = frameGreen,
                            scale = scale
                        )
                        ListPillButtonMint(
                            textLine1 = "לצפייה",
                            textLine2 = "ברשימת תרומה",
                            onClick = onViewDonationList,
                            icon = painterResource(Res.drawable.stage2_donation),
                            green = Stage2Colors.FrameBlue,
                            scale = scale
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp * scale),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(6.dp * scale))
                        Text(
                            text = "סופר \"איזי\"",
                            color = brandGreen,
                            fontSize = (52.sp * scale),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(25.dp * scale))
                        Image(
                            painter = painterResource(Res.drawable.logo_super_easy),
                            contentDescription = null,
                            modifier = Modifier.size(130.dp * scale)
                        )
                        Spacer(Modifier.height(8.dp * scale))

                        Spacer(Modifier.weight(1f))

                        InstructionsRow(
                            right = "לעיון בקטגוריות\nלחצו על \"קטגוריות\"",
                            center = "לעיון בסל הקניות\nלחצו על \"סל קניות\"",
                            left = "לחיפוש מוצר\nלחצו על \"חיפוש\"",
                            color = bodyText,
                            scale = scale
                        )

                        Spacer(Modifier.height(14.dp * scale))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(144.dp * scale)
                                .clip(RoundedCornerShape(18.dp * scale))
                                .background(frameGreen)
                                .padding(horizontal = 16.dp * scale, vertical = 16.dp * scale)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(18.dp * scale),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                MenuOutlinedButton(
                                    label = "קטגוריות",
                                    icon = painterResource(Res.drawable.stage2_baggage),
                                    onClick = onCategories,
                                    modifier = Modifier.weight(1f),
                                    scale = scale,
                                    frameGreen = frameGreen,
                                    tileWhite = tileWhite
                                )
                                MenuOutlinedButton(
                                    label = "סל קניות",
                                    icon = painterResource(Res.drawable.stage2_cart),
                                    onClick = onCart,
                                    modifier = Modifier.weight(1f),
                                    scale = scale,
                                    frameGreen = frameGreen,
                                    tileWhite = tileWhite
                                )
                                MenuOutlinedButton(
                                    label = "חיפוש",
                                    icon = painterResource(Res.drawable.stage2_magnifying_glass),
                                    onClick = onSearch,
                                    modifier = Modifier.weight(1f),
                                    scale = scale,
                                    frameGreen = frameGreen,
                                    tileWhite = tileWhite
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp * scale))
                    }
                }
            }
        }
    }
}

// InstructionsRow + InstructionText
//Divides the three texts horizontally with spaces, large text, centered.
@Composable
private fun InstructionsRow(
    right: String,
    center: String,
    left: String,
    color: androidx.compose.ui.graphics.Color,
    scale: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp * scale)
    ) {
        InstructionText(text = right, color = color, scale = scale, modifier = Modifier.weight(1f))
        InstructionText(text = center, color = color, scale = scale, modifier = Modifier.weight(1f))
        InstructionText(text = left, color = color, scale = scale, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun InstructionText(
    text: String,
    color: androidx.compose.ui.graphics.Color,
    scale: Float,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        fontSize = (26.sp * scale),
        lineHeight = (34.sp * scale),
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(vertical = 4.dp * scale)
    )
}

@Composable
private fun MenuOutlinedButton(
    label: String,
    icon: androidx.compose.ui.graphics.painter.Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    frameGreen: androidx.compose.ui.graphics.Color,
    tileWhite: androidx.compose.ui.graphics.Color
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp * scale)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = tileWhite,
            contentColor = frameGreen
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 2.dp * scale,
            brush = SolidColor(frameGreen)
        ),
        shape = RoundedCornerShape(16.dp * scale),
        contentPadding = PaddingValues(horizontal = 18.dp * scale, vertical = 10.dp * scale)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontSize = (32.sp * scale),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.width(12.dp * scale))
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(72.dp * scale)
            )
        }
    }
}

@Composable
private fun ListPillButtonMint(
    textLine1: String,
    textLine2: String,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.painter.Painter,
    green: androidx.compose.ui.graphics.Color,
    scale: Float
) {
    val mint = Stage2Colors.Mint
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp * scale),
        colors = ButtonDefaults.buttonColors(
            containerColor = mint,
            contentColor = green
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        contentPadding = PaddingValues(horizontal = 14.dp * scale, vertical = 10.dp * scale),
        modifier = Modifier
            .shadow(6.dp * scale, RoundedCornerShape(12.dp * scale), clip = false)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = textLine1,
                    fontSize = (18.sp * scale),
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = (20.sp * scale),
                    color = green
                )
                Text(
                    text = textLine2,
                    fontSize = (18.sp * scale),
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = (20.sp * scale),
                    color = green
                )
            }
            Spacer(Modifier.width(10.dp * scale))
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(22.dp * scale),
                tint = green
            )
        }
    }
}
