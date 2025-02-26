<<<<<<<< HEAD:composeApp/src/commonMain/kotlin/org/example/hit/heal/evaluations/presentaion/components/RoundedButton.kt
package org.example.hit.heal.evaluations.presentaion.components
========
package org.example.hit.heal.core.presentation.components
>>>>>>>> main:ui/core/src/commonMain/kotlin/org/example/hit/heal/core/presentation/components/RoundedButton.kt

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
<<<<<<<< HEAD:composeApp/src/commonMain/kotlin/org/example/hit/heal/evaluations/presentaion/components/RoundedButton.kt
import org.example.hit.heal.evaluations.presentaion.primaryColor
========
import org.example.hit.heal.core.presentation.Colors
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

>>>>>>>> main:ui/core/src/commonMain/kotlin/org/example/hit/heal/core/presentation/components/RoundedButton.kt

@Composable
fun RoundedButton(text: String, modifier: Modifier, onclick: () -> Unit) {
    Button(
        onClick = onclick, colors = ButtonDefaults.buttonColors(Colors.primaryColor),
        shape = RoundedCornerShape(50),
        modifier = modifier,
    ) {
        Text(text, color = Color.White, fontSize = 32.sp, modifier = Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
fun RoundedButton(stringResource: StringResource, modifier: Modifier, onclick: () -> Unit) {
    RoundedButton(stringResource(stringResource), modifier, onclick)
}