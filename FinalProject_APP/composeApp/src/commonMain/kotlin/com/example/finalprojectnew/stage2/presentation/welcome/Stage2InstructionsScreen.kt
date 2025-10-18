package com.example.finalprojectnew.stage2.presentation.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectnew.stage2.presentation.Stage2Colors
import com.example.finalprojectnew.stage2.presentation.components.PrimaryMenuButton
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing

@Composable
fun Stage2InstructionsScreen(
    onStartShopping: () -> Unit
) {
    val screenBg   = Stage2Colors.ScreenBg
    val brandGreen = Stage2Colors.BrandGreen
    val bodyText   = Stage2Colors.BodyText

    val items = listOf(
        "עליך לבצע קנייה שבועית למשפחה לפי רשימת הקניות שמופיעה על המסך. יש לבחור רק את המצרכים המופיעים ברשימה.",
        "ניתן לבחור מצרכים דרך הקטגוריות או בשורת החיפוש.",
        "בנוסף, יש לאסוף את כל המצרכים שמופיעים ברשימת התרומה, ומסומנים בכחול."
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            color = screenBg,
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.safeDrawing.asPaddingValues())
        ) {
            BoxWithConstraints(Modifier.fillMaxSize()) {
                val w = maxWidth.value
                val scale =
                    when {
                        w >= 1000f -> 1.40f
                        w >= 800f  -> 1.25f
                        w >= 600f  -> 1.12f
                        else       -> 1.00f
                    }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 28.dp * scale)
                        .widthIn(max = 760.dp * scale),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "הוראות",
                        color = brandGreen,
                        fontSize = (60.sp * scale),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(18.dp * scale))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp * scale),
                        horizontalAlignment = Alignment.Start
                    ) {
                        items.forEachIndexed { index, line ->
                            Text(
                                text = "• $line",
                                color = bodyText,
                                fontSize = (32.sp * scale),
                                lineHeight = (40.sp * scale),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (index != items.lastIndex) {
                                Spacer(Modifier.height(14.dp * scale))
                            }
                        }
                    }

                    Spacer(Modifier.height(26.dp * scale))

                    PrimaryMenuButton(
                        text = "לחצו לתחילת הקנייה",
                        onClick = onStartShopping,
                        modifier = Modifier
                            .height(75.dp * scale)
                            .padding(horizontal = 48.dp * scale),
                        textSizeSp = (30f * scale),
                        corner = (20.dp * scale)
                    )

                    Spacer(Modifier.height(24.dp * scale))
                }
            }
        }
    }
}
