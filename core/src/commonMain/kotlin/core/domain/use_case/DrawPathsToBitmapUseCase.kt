package core.domain.use_case

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.example.hit.heal.core.presentation.primaryColor

object DrawPathsToBitmapUseCase {

    fun drawPathsToBitmap(
        canvasSize: Size,
        paths: List<Path>,
        density: Density,
        pathColor: Color = primaryColor,
        backgroundColor: Color = Color.White,
    ): ImageBitmap {
        val bitmap = ImageBitmap(canvasSize.width.toInt(), canvasSize.height.toInt())
        val canvas = Canvas(bitmap)
        val drawScope = CanvasDrawScope()

        drawScope.draw(
            density = density,
            layoutDirection = LayoutDirection.Ltr,
            canvas = canvas,
            size = canvasSize
        ) {
            drawRect(
                color = backgroundColor,
                size = size
            )
            paths.forEach { path ->
                drawPath(
                    path = path,
                    color = pathColor,
                    style = Stroke(width = 4.dp.toPx())
                )
            }
        }

        return bitmap
    }
}