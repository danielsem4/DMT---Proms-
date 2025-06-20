
import androidx.compose.runtime.Composable
import org.example.hit.heal.core.presentation.Resources.Icon.ball
import org.example.hit.heal.core.presentation.Resources.Icon.balloon
import org.example.hit.heal.core.presentation.Resources.Icon.lemon
import org.example.hit.heal.core.presentation.Resources.Icon.pencil
import org.example.hit.heal.core.presentation.Resources.Icon.ruler
import org.example.hit.heal.core.presentation.Resources.Icon.table
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberBall
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberBalloon
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberLemon
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberPencil
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberRuler
import org.example.hit.heal.core.presentation.Resources.String.fourthQuestionHitberTable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun getImageName(imageId: DrawableResource): String {
    return when (imageId) {
        pencil -> stringResource(fourthQuestionHitberPencil)
        ruler -> stringResource(fourthQuestionHitberRuler)
        table -> stringResource(fourthQuestionHitberTable)
        ball -> stringResource(fourthQuestionHitberBall)
        balloon -> stringResource(fourthQuestionHitberBalloon)
        lemon -> stringResource(fourthQuestionHitberLemon)
        else -> ""
    }
}
