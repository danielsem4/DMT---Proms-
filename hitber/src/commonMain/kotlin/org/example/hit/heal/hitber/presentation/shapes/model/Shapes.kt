package org.example.hit.heal.hitber.presentation.shapes.model

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
import dmt_proms.hitber.generated.resources.star
import dmt_proms.hitber.generated.resources.trapezoid
import dmt_proms.hitber.generated.resources.triangle
import org.jetbrains.compose.resources.DrawableResource

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
