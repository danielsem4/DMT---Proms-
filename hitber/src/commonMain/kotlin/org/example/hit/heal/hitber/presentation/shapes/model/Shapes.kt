package org.example.hit.heal.hitber.presentation.shapes.model

import org.example.hit.heal.core.presentation.Resources.Icon.asteriskIcon
import org.example.hit.heal.core.presentation.Resources.Icon.cone
import org.example.hit.heal.core.presentation.Resources.Icon.checkIcon
import org.example.hit.heal.core.presentation.Resources.Icon.elipsaIcon
import org.example.hit.heal.core.presentation.Resources.Icon.hashMarkIcon
import org.example.hit.heal.core.presentation.Resources.Icon.hexagonIcon
import org.example.hit.heal.core.presentation.Resources.Icon.pentagonIcon
import org.example.hit.heal.core.presentation.Resources.Icon.plusIcon
import org.example.hit.heal.core.presentation.Resources.Icon.rectangleIcon
import org.example.hit.heal.core.presentation.Resources.Icon.rhombusIcon
import org.example.hit.heal.core.presentation.Resources.Icon.starOutlineIcon
import org.example.hit.heal.core.presentation.Resources.Icon.trapezeIcon
import org.example.hit.heal.core.presentation.Resources.Icon.triangle90Icon
import org.example.hit.heal.core.presentation.Resources.Icon.triangleIcon
import org.example.hit.heal.core.presentation.Resources.Icon.xIcon
import org.jetbrains.compose.resources.DrawableResource

// Predefined shape sets used for random selection
val shapeSets = listOf(
    listOf(ShapeType.Triangle, ShapeType.Check, ShapeType.Cone, ShapeType.Pentagon, ShapeType.Star),
    listOf(ShapeType.Trapezoid, ShapeType.Plus, ShapeType.Hexagon, ShapeType.RightTriangle, ShapeType.Hashtag),
    listOf(ShapeType.Triangle, ShapeType.Close, ShapeType.Ellipse, ShapeType.Rhomb, ShapeType.Star),
    listOf(ShapeType.Rectangle, ShapeType.Check, ShapeType.Pentagon, ShapeType.Hexagon, ShapeType.Hashtag),
    listOf(ShapeType.Rectangle, ShapeType.Plus, ShapeType.Ellipse, ShapeType.RightTriangle, ShapeType.Star)
)

//Represents the different types of shape
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

// Data class representing a shape with a type and an icon
data class Shape(val type: ShapeType, val drawable: DrawableResource)

// List of all available shapes, each paired with its visual icon
val shapeList = listOf(
    Shape(ShapeType.Ellipse, elipsaIcon),
    Shape(ShapeType.Rectangle, rectangleIcon),
    Shape(ShapeType.Pentagon, pentagonIcon),
    Shape(ShapeType.Star, starOutlineIcon),
    Shape(ShapeType.Hashtag, hashMarkIcon),
    Shape(ShapeType.Rhomb, rhombusIcon),
    Shape(ShapeType.Cone, cone),
    Shape(ShapeType.Plus, plusIcon),
    Shape(ShapeType.Hexagon, hexagonIcon),
    Shape(ShapeType.RightTriangle, triangle90Icon),
    Shape(ShapeType.Asterisk, asteriskIcon),
    Shape(ShapeType.Check, checkIcon),
    Shape(ShapeType.Trapezoid, trapezeIcon),
    Shape(ShapeType.Triangle, triangleIcon),
    Shape(ShapeType.Close, xIcon)
)
