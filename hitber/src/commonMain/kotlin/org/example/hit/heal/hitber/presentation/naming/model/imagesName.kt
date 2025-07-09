
import androidx.compose.runtime.Composable
import org.example.hit.heal.core.presentation.Resources.Icon.ballIcon
import org.example.hit.heal.core.presentation.Resources.Icon.balloonIcon
import org.example.hit.heal.core.presentation.Resources.Icon.lemon
import org.example.hit.heal.core.presentation.Resources.Icon.pencilIcon
import org.example.hit.heal.core.presentation.Resources.Icon.rulerIcon
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
        pencilIcon -> stringResource(fourthQuestionHitberPencil)
        rulerIcon -> stringResource(fourthQuestionHitberRuler)
        table -> stringResource(fourthQuestionHitberTable)
        ballIcon -> stringResource(fourthQuestionHitberBall)
        balloonIcon -> stringResource(fourthQuestionHitberBalloon)
        lemon -> stringResource(fourthQuestionHitberLemon)
        else -> ""
    }
}
