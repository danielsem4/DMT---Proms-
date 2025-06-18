package org.example.hit.heal.hitber.presentation.shapes.components

import androidx.compose.runtime.Composable
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberAsterisk
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberCheck
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberClose
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberCone
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberEllipse
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberHashTag
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberHexagon
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberPentagon
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberPlus
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberRectangle
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberRhomb
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberRightTriangle
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberStar
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTrapezoid
import org.example.hit.heal.core.presentation.Resources.String.secondQuestionHitberTriangle
import org.example.hit.heal.hitber.presentation.shapes.model.ShapeType
import org.jetbrains.compose.resources.stringResource


@Composable
fun getShapeName(shapeType: ShapeType): String {
    return when (shapeType) {
        ShapeType.Triangle -> stringResource(secondQuestionHitberTriangle)
        ShapeType.Star -> stringResource(secondQuestionHitberStar)
        ShapeType.Ellipse -> stringResource(secondQuestionHitberEllipse)
        ShapeType.Rectangle -> stringResource(secondQuestionHitberRectangle)
        ShapeType.Pentagon -> stringResource(secondQuestionHitberPentagon)
        ShapeType.Hashtag -> stringResource(secondQuestionHitberHashTag)
        ShapeType.Rhomb -> stringResource(secondQuestionHitberRhomb)
        ShapeType.Cone -> stringResource(secondQuestionHitberCone)
        ShapeType.Plus -> stringResource(secondQuestionHitberPlus)
        ShapeType.Hexagon -> stringResource(secondQuestionHitberHexagon)
        ShapeType.RightTriangle -> stringResource(secondQuestionHitberRightTriangle)
        ShapeType.Asterisk -> stringResource(secondQuestionHitberAsterisk)
        ShapeType.Check -> stringResource(secondQuestionHitberCheck)
        ShapeType.Trapezoid -> stringResource(secondQuestionHitberTrapezoid)
        ShapeType.Close -> stringResource(secondQuestionHitberClose)
    }
}


