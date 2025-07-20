package com.example.new_memory_test.presentation.screens.ScheduleInformationScreen.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.FontSize.EXTRA_REGULAR
import org.example.hit.heal.core.presentation.Sizes.iconSizeLg
import org.example.hit.heal.core.presentation.Sizes.spacingMd
import org.example.hit.heal.core.presentation.Sizes.spacingSm


//Only for Image and Item in 1 row
@Composable
fun ActivityItem(text: String, icon: Painter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacingMd, vertical = spacingSm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Right,
            fontSize = EXTRA_REGULAR ,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
        )
        Spacer(modifier = Modifier.size(3.dp))
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(iconSizeLg) //need check
        )
    }
}
