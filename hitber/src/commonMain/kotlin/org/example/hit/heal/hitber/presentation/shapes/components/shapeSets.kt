package org.example.hit.heal.hitber.presentation.shapes.components

import androidx.compose.runtime.Composable
import dmt_proms.hitber.generated.resources.Res
import dmt_proms.hitber.generated.resources.asterisk
import dmt_proms.hitber.generated.resources.check
import dmt_proms.hitber.generated.resources.close
import dmt_proms.hitber.generated.resources.cone
import dmt_proms.hitber.generated.resources.ellipse
import dmt_proms.hitber.generated.resources.hash_tag
import dmt_proms.hitber.generated.resources.hexagon
import dmt_proms.hitber.generated.resources.pentagon
import dmt_proms.hitber.generated.resources.plus
import dmt_proms.hitber.generated.resources.rectangle
import dmt_proms.hitber.generated.resources.rhomb
import dmt_proms.hitber.generated.resources.right_triangle
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
import dmt_proms.hitber.generated.resources.star
import dmt_proms.hitber.generated.resources.trapezoid
import dmt_proms.hitber.generated.resources.triangle
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

val shapeSets = listOf(
    listOf(ShapeType.Triangle, ShapeType.Check, ShapeType.Cone, ShapeType.Pentagon, ShapeType.Star),
    listOf(ShapeType.Trapezoid, ShapeType.Plus, ShapeType.Hexagon, ShapeType.RightTriangle, ShapeType.Hashtag),
    listOf(ShapeType.Triangle, ShapeType.Close, ShapeType.Ellipse, ShapeType.Rhomb, ShapeType.Star),
    listOf(ShapeType.Rectangle, ShapeType.Check, ShapeType.Pentagon, ShapeType.Hexagon, ShapeType.Hashtag),
    listOf(ShapeType.Rectangle, ShapeType.Plus, ShapeType.Ellipse, ShapeType.RightTriangle, ShapeType.Star)
)

enum class ShapeType {
    Ellipse,
    Rectangle,
    Pentagon,
    Star,
    Hashtag,
    Rhomb,
    Cone,
    Plus,
    Hexagon,
    RightTriangle,
    Asterisk,
    Check,
    Trapezoid,
    Triangle,
    Close
}

data class Shape(val type: ShapeType, val drawable: DrawableResource)

val shapeList = listOf(
    Shape(ShapeType.Ellipse, Res.drawable.ellipse),
    Shape(ShapeType.Rectangle, Res.drawable.rectangle),
    Shape(ShapeType.Pentagon, Res.drawable.pentagon),
    Shape(ShapeType.Star, Res.drawable.star),
    Shape(ShapeType.Hashtag, Res.drawable.hash_tag),
    Shape(ShapeType.Rhomb, Res.drawable.rhomb),
    Shape(ShapeType.Cone, Res.drawable.cone),
    Shape(ShapeType.Plus, Res.drawable.plus),
    Shape(ShapeType.Hexagon, Res.drawable.hexagon),
    Shape(ShapeType.RightTriangle, Res.drawable.right_triangle),
    Shape(ShapeType.Asterisk, Res.drawable.asterisk),
    Shape(ShapeType.Check, Res.drawable.check),
    Shape(ShapeType.Trapezoid, Res.drawable.trapezoid),
    Shape(ShapeType.Triangle, Res.drawable.triangle),
    Shape(ShapeType.Close, Res.drawable.close)
)

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


