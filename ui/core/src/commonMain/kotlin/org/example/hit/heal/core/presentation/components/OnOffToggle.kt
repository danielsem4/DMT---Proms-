package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.FontSize
import org.example.hit.heal.core.presentation.Resources
import org.example.hit.heal.core.presentation.Sizes
import org.example.hit.heal.core.presentation.primaryColor
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OnOffToggle(
    checked: Boolean?,
    onCheckedChange: (Boolean) -> Unit,
    onText: String = stringResource(Resources.String.on),
    offText: String = stringResource(Resources.String.off)
) {
    Row(horizontalArrangement = Arrangement.Center) {
        ToggleBtn(
            isChecked = checked == true,
            onCheckedChange = { onCheckedChange(true) },
            text = onText
        )

        Spacer(Modifier.weight(0.1f))

        ToggleBtn(
            isChecked = checked == false,
            onCheckedChange = { onCheckedChange(false) },
            text = offText
        )
    }
}

@Composable
private fun RowScope.ToggleBtn(
    isChecked: Boolean?,
    onCheckedChange: (Boolean) -> Unit,
    text: String
) {
    val shape = RoundedCornerShape(Sizes.radiusXl)
    Box(
        modifier = Modifier
            .weight(1f)
            .background(
                if (isChecked == true) primaryColor
                else Color.White,
                shape = shape
            )
            .border(2.dp, Color.Black, shape = shape)
            .clickable { onCheckedChange(false) }
            .padding(vertical = Sizes.paddingMd),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = FontSize.LARGE,
            fontWeight = FontWeight.Bold,
            color = if (isChecked == false) primaryColor else Color.White
        )
    }
}


@Composable
@Preview
fun OnOffTogglePreview() {
    var checked by remember { mutableStateOf<Boolean?>(null) }

    OnOffToggle(
        checked = checked,
        onCheckedChange = { checked = it },
        onText = "On",
        offText = "Off"
    )
}
