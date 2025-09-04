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
    listOf(ShapeType.Hashtag, ShapeType.Pentagon, ShapeType.Trapezoid, ShapeType.Close, ShapeType.Triangle),
    listOf(ShapeType.Star, ShapeType.Hexagon, ShapeType.Rhomb, ShapeType.Check, ShapeType.Ellipse),
    listOf(ShapeType.Hashtag, ShapeType.Hexagon, ShapeType.Close, ShapeType.Ellipse, ShapeType.Triangle),
    listOf(ShapeType.Star, ShapeType.Pentagon, ShapeType.Rhomb, ShapeType.Check, ShapeType.Triangle),
    listOf(ShapeType.Ellipse, ShapeType.Close, ShapeType.Trapezoid, ShapeType.Hexagon, ShapeType.Star)
)

//Represents the different types of shape
enum class ShapeType {
    Ellipse,
    Pentagon,
    Star,
    Hashtag,
    Rhomb,
    Hexagon,
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
    Shape(ShapeType.Pentagon, pentagonIcon),
    Shape(ShapeType.Star, starOutlineIcon),
    Shape(ShapeType.Hashtag, hashMarkIcon),
    Shape(ShapeType.Rhomb, rhombusIcon),
    Shape(ShapeType.Hexagon, hexagonIcon),
    Shape(ShapeType.Check, checkIcon),
    Shape(ShapeType.Trapezoid, trapezeIcon),
    Shape(ShapeType.Triangle, triangleIcon),
    Shape(ShapeType.Close, xIcon)
)
