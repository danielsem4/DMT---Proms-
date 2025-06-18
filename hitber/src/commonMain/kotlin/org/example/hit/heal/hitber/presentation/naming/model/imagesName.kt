
import androidx.compose.runtime.Composable
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.ball
import dmt_proms.hitber.generated.resources.balloon
import dmt_proms.hitber.generated.resources.lemon
import dmt_proms.hitber.generated.resources.pencil
import dmt_proms.hitber.generated.resources.ruler
import dmt_proms.hitber.generated.resources.table
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
        Res.drawable.pencil -> stringResource(fourthQuestionHitberPencil)
        Res.drawable.ruler -> stringResource(fourthQuestionHitberRuler)
        Res.drawable.table -> stringResource(fourthQuestionHitberTable)
        Res.drawable.ball -> stringResource(fourthQuestionHitberBall)
        Res.drawable.balloon -> stringResource(fourthQuestionHitberBalloon)
        Res.drawable.lemon -> stringResource(fourthQuestionHitberLemon)
        else -> ""
    }
}
