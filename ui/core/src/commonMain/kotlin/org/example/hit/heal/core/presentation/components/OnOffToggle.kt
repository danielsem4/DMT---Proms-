package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnOffToggle(
    checked: Boolean?,
    onCheckedChange: (Boolean) -> Unit,
    onText: String = stringResource(Resources.String.on),
    offText: String = stringResource(Resources.String.off)
) {
    Row(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (checked == true) primaryColor else Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                .clickable { onCheckedChange(true) }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = onText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (checked == true) Color.White else primaryColor
            )
        }

        Spacer(Modifier.weight(0.1f))

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (checked == false) primaryColor else Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                .clickable { onCheckedChange(false) }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = offText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (checked == false) Color.White else primaryColor
            )
        }
    }
}
