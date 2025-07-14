package org.example.hit.heal.hitber.presentation.naming.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberWhatInThePic
import org.example.hit.heal.core.presentation.Sizes.paddingMd
import org.example.hit.heal.core.presentation.Sizes.paddingSm
import org.example.hit.heal.core.presentation.components.SimpleInputText
import org.example.hit.heal.hitber.presentation.naming.FourthQuestionViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun NamingTextFields(
    answer1: String,
    answer2: String,
    viewModel: FourthQuestionViewModel
) {
    val label = stringResource(fourthQuestionHitberWhatInThePic)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(bottom = paddingMd)
    ) {
        SimpleInputText(
            value = answer1,
            onValueChange = viewModel::onAnswer1Changed,
            label = label,
            modifier = Modifier.weight(1f).padding(end = paddingSm)
        )

        SimpleInputText(
            value = answer2,
            onValueChange = viewModel::onAnswer2Changed,
            label = label,
            modifier = Modifier.weight(1f).padding(start = paddingSm)
        )

    }
}
