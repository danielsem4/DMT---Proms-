package org.example.hit.heal.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
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
    maxLines: Int = 4
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        maxLines = maxLines,

        decorationBox = { innerTextField ->
            Box(modifier) {
                if (value.isEmpty()) {
                    Text(
                        text = hintText,
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                }
                innerTextField()
            }
        }

//        modifier = Modifier.onFocusChanged {
//            it.isFocused
//        }
    )

}