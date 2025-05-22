
import androidx.compose.runtime.Composable
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.ball
import dmt_proms.hitber.generated.resources.balloon
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_ball
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_balloon
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_lemon
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_pencil
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_ruler
import dmt_proms.hitber.generated.resources.fourth_question_hitbear_table
import dmt_proms.hitber.generated.resources.lemon
import dmt_proms.hitber.generated.resources.pencil
import dmt_proms.hitber.generated.resources.ruler
import dmt_proms.hitber.generated.resources.table
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun getImageName(imageId: DrawableResource): String {
    return when (imageId) {
        Res.drawable.pencil -> stringResource(Res.string.fourth_question_hitbear_pencil)
        Res.drawable.ruler -> stringResource(Res.string.fourth_question_hitbear_ruler)
        Res.drawable.table -> stringResource(Res.string.fourth_question_hitbear_table)
        Res.drawable.ball -> stringResource(Res.string.fourth_question_hitbear_ball)
        Res.drawable.balloon -> stringResource(Res.string.fourth_question_hitbear_balloon)
        Res.drawable.lemon -> stringResource(Res.string.fourth_question_hitbear_lemon)
        else -> ""
    }
}
