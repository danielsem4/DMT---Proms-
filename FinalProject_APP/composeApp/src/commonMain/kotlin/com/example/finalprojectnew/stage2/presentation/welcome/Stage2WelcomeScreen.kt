// first screen of stage2
package com.example.finalprojectnew.stage2.presentation.welcome

import androidx.compose.foundation.Image
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
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.logo_super_easy
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing

@Composable
fun Stage2WelcomeScreen(
    onViewListClick: () -> Unit
) {
    val screenBg = Stage2Colors.ScreenBg
    val brandGreen = Stage2Colors.BrandGreen
    val bodyText = Stage2Colors.BodyText

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) { // right to left
        Surface(
            color = screenBg,
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.safeDrawing.asPaddingValues())
        ) {
            BoxWithConstraints(Modifier.fillMaxSize()) { //
                val w = maxWidth.value
                val scale =
                    when {
                        w >= 1000f -> 1.40f
                        w >= 800f -> 1.25f
                        w >= 600f -> 1.12f
                        else -> 1.00f
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
                        text = "ברוכים הבאים לסופרמרקט איזי אונליין",
                        color = brandGreen,
                        fontSize = (50.sp * scale),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(14.dp * scale))

                    Image(
                        painter = painterResource(Res.drawable.logo_super_easy),
                        contentDescription = null,
                        modifier = Modifier.size(130.dp * scale)
                    )

                    Spacer(Modifier.height(14.dp * scale))

                    Text(
                        text =
                            "במשימה זו יש לבחור מצרכים שונים, להוסיף אותם לסל הקניות ולאחר מכן ללחוץ על 'סיום'.\n" +
                                    "* יש לעקוב אחר ההנחיות שיתקבלו בהמשך.\n" +
                                    "* יש לבצע את הפעילות בסביבה שקטה ככל שניתן",
                        color = bodyText,
                        fontSize = (30.sp * scale),
                        lineHeight = (46.sp * scale),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp * scale)
                    )

                    Spacer(Modifier.height(22.dp * scale))

                    PrimaryMenuButton(
                        text = "לחצו לקריאת ההוראות",
                        onClick = onViewListClick,
                        modifier = Modifier
                            .height(75.dp * scale)
                            .padding(horizontal = 48.dp * scale),
                        textSizeSp = (26f * scale),
                        corner = (20.dp * scale),
                        icon = null
                    )

                    Spacer(Modifier.height(24.dp * scale))
                }
            }
        }
    }
}
