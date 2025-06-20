package org.example.hit.heal.hitber.presentation.shapes.model

import org.example.hit.heal.core.presentation.Resources.Icon.asterisk
import org.example.hit.heal.core.presentation.Resources.Icon.close
import org.example.hit.heal.core.presentation.Resources.Icon.cone
import org.example.hit.heal.core.presentation.Resources.Icon.ellipse
import org.example.hit.heal.core.presentation.Resources.Icon.hashTag
import org.example.hit.heal.core.presentation.Resources.Icon.hexagon
import org.example.hit.heal.core.presentation.Resources.Icon.pentagon
import org.example.hit.heal.core.presentation.Resources.Icon.plus
import org.example.hit.heal.core.presentation.Resources.Icon.rectangle
import org.example.hit.heal.core.presentation.Resources.Icon.rhomb
import org.example.hit.heal.core.presentation.Resources.Icon.rightTriangle
import org.example.hit.heal.core.presentation.Resources.Icon.star
import org.example.hit.heal.core.presentation.Resources.Icon.trapezoid
import org.example.hit.heal.core.presentation.Resources.Icon.triangle
import org.example.hit.heal.core.presentation.Resources.Icon.check
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
    Shape(ShapeType.Ellipse, ellipse),
    Shape(ShapeType.Rectangle, rectangle),
    Shape(ShapeType.Pentagon, pentagon),
    Shape(ShapeType.Star, star),
    Shape(ShapeType.Hashtag, hashTag),
    Shape(ShapeType.Rhomb, rhomb),
    Shape(ShapeType.Cone, cone),
    Shape(ShapeType.Plus, plus),
    Shape(ShapeType.Hexagon, hexagon),
    Shape(ShapeType.RightTriangle, rightTriangle),
    Shape(ShapeType.Asterisk, asterisk),
    Shape(ShapeType.Check, check),
    Shape(ShapeType.Trapezoid, trapezoid),
    Shape(ShapeType.Triangle, triangle),
    Shape(ShapeType.Close, close)
)
