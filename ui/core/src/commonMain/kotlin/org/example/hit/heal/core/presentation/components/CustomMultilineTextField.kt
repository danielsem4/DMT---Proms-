package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dmt_proms.ui.core.generated.resources.Res
import dmt_proms.ui.core.generated.resources.hint_open_question
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomMultilineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = stringResource(Res.string.hint_open_question),
    textStyle: TextStyle = MaterialTheme.typography.body1,
    maxLines: Int = 4,
    minHeight: Dp = 120.dp
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        maxLines = maxLines,
        modifier = Modifier
            .defaultMinSize(minHeight = minHeight),
        decorationBox = { innerTextField ->
            Box(
                modifier = modifier
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .defaultMinSize(minHeight = minHeight)
                    .padding(16.dp)
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = hintText,
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                }
                innerTextField()
            }
        }
    )
}