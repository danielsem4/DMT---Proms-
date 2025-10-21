package com.example.finalprojectnew.stage2.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.*

private val FrameGreen = Color(0xFF0A4E2A) //green color for button frames/text

@Composable
fun Stage2CategorySidebar(
    current: String, //the current category
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    scale: Float = 1f
) {
    val items: List<Triple<String, String, DrawableResource>> = listOf(
        Triple("milk", "מוצרי חלב", Res.drawable.stage2_category_milk),
        Triple("meat", "מוצרי בשר", Res.drawable.stage2_category_meat),
        Triple("vegetables", "ירקות", Res.drawable.stage2_category_vegetables),
        Triple("fruits", "פירות", Res.drawable.stage2_category_fruits),
        Triple("frozen", "קפואים", Res.drawable.stage2_category_frozen),
        Triple("dry", "יבשים ותבלינים", Res.drawable.stage2_category_dry),
        Triple("pastries", "מאפים", Res.drawable.stage2_category_pastries),
        Triple("cleaning", "ניקיון וחד''פ", Res.drawable.stage2_category_cleaning)
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp * scale)
    ) {
        items.forEach { (id, title, iconRes) ->
            // selected — Is this the button for the current category?
            // If so, color the button background with light yellow. otherwise, a light mint background.
            val selected = id == current
            Button(
                onClick = { onSelect(id) },
                shape = RoundedCornerShape(14.dp * scale),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected) Color(0xFFFFE08A) else Color(0xFFF7FBF7),
                    contentColor = FrameGreen
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp),
                contentPadding = PaddingValues(
                    horizontal = 14.dp * scale,
                    vertical = 10.dp * scale
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        title,
                        fontSize = (22.sp * scale),
                        fontWeight = FontWeight.ExtraBold,
                        color = FrameGreen
                    )
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp * scale)
                    )
                }
            }
        }
    }
}