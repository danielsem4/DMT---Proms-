package org.example.hit.heal.hitber.presentation.shapes.components

import androidx.compose.runtime.Composable
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.second_question_hitbear_asterisk
import dmt_proms.hitber.generated.resources.second_question_hitbear_check
import dmt_proms.hitber.generated.resources.second_question_hitbear_close
import dmt_proms.hitber.generated.resources.second_question_hitbear_cone
import dmt_proms.hitber.generated.resources.second_question_hitbear_ellipse
import dmt_proms.hitber.generated.resources.second_question_hitbear_hash_tag
import dmt_proms.hitber.generated.resources.second_question_hitbear_hexagon
import dmt_proms.hitber.generated.resources.second_question_hitbear_pentagon
import dmt_proms.hitber.generated.resources.second_question_hitbear_plus
import dmt_proms.hitber.generated.resources.second_question_hitbear_rectangle
import dmt_proms.hitber.generated.resources.second_question_hitbear_rhomb
import dmt_proms.hitber.generated.resources.second_question_hitbear_right_triangle
import dmt_proms.hitber.generated.resources.second_question_hitbear_star
import dmt_proms.hitber.generated.resources.second_question_hitbear_trapezoid
import dmt_proms.hitber.generated.resources.second_question_hitbear_triangle
import org.example.hit.heal.hitber.presentation.shapes.model.ShapeType
import org.jetbrains.compose.resources.stringResource


@Composable
fun getShapeName(shapeType: ShapeType): String {
    return when (shapeType) {
        ShapeType.Triangle -> stringResource(Res.string.second_question_hitbear_triangle)
        ShapeType.Star -> stringResource(Res.string.second_question_hitbear_star)
        ShapeType.Ellipse -> stringResource(Res.string.second_question_hitbear_ellipse)
        ShapeType.Rectangle -> stringResource(Res.string.second_question_hitbear_rectangle)
        ShapeType.Pentagon -> stringResource(Res.string.second_question_hitbear_pentagon)
        ShapeType.Hashtag -> stringResource(Res.string.second_question_hitbear_hash_tag)
        ShapeType.Rhomb -> stringResource(Res.string.second_question_hitbear_rhomb)
        ShapeType.Cone -> stringResource(Res.string.second_question_hitbear_cone)
        ShapeType.Plus -> stringResource(Res.string.second_question_hitbear_plus)
        ShapeType.Hexagon -> stringResource(Res.string.second_question_hitbear_hexagon)
        ShapeType.RightTriangle -> stringResource(Res.string.second_question_hitbear_right_triangle)
        ShapeType.Asterisk -> stringResource(Res.string.second_question_hitbear_asterisk)
        ShapeType.Check -> stringResource(Res.string.second_question_hitbear_check)
        ShapeType.Trapezoid -> stringResource(Res.string.second_question_hitbear_trapezoid)
        ShapeType.Close -> stringResource(Res.string.second_question_hitbear_close)
    }
}


