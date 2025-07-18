import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

public val Icons.MidPainIcon: ImageVector
    get() {
        if (_midPainIcon != null) {
            return _midPainIcon!!
        }
        _midPainIcon =
            Builder(
                    name = "MidPainIcon",
                    defaultWidth = 200.0.dp,
                    defaultHeight = 200.0.dp,
                    viewportWidth = 860.0f,
                    viewportHeight = 856.0f,
                )
                .apply {
                    group {
                        path(
                            fill = SolidColor(Color(0xFFFCC812)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(81.0f, 32.0f)
                            horizontalLineToRelative(672.0f)
                            lineToRelative(11.0f, 4.0f)
                            lineToRelative(19.0f, 9.0f)
                            lineToRelative(7.0f, 7.0f)
                            lineToRelative(2.0f, 1.0f)
                            verticalLineToRelative(2.0f)
                            lineToRelative(4.0f, 2.0f)
                            verticalLineToRelative(2.0f)
                            lineToRelative(4.0f, 2.0f)
                            lineToRelative(8.0f, 10.0f)
                            lineToRelative(4.0f, 9.0f)
                            lineToRelative(4.0f, 14.0f)
                            verticalLineToRelative(642.0f)
                            lineToRelative(-1.0f, 7.0f)
                            lineToRelative(-2.0f, 2.0f)
                            lineToRelative(-2.0f, 10.0f)
                            lineToRelative(-5.0f, 9.0f)
                            lineToRelative(-9.0f, 10.0f)
                            lineToRelative(-3.0f, 5.0f)
                            lineToRelative(-7.0f, 4.0f)
                            lineToRelative(-5.0f, 4.0f)
                            lineToRelative(-8.0f, 5.0f)
                            lineToRelative(-4.0f, 3.0f)
                            lineToRelative(-13.0f, 2.0f)
                            lineToRelative(-2.0f, 2.0f)
                            lineToRelative(-17.0f, 1.0f)
                            horizontalLineToRelative(-654.0f)
                            lineToRelative(-4.0f, -1.0f)
                            lineToRelative(-1.0f, -2.0f)
                            lineToRelative(-10.0f, -2.0f)
                            lineToRelative(-10.0f, -6.0f)
                            lineToRelative(-7.0f, -7.0f)
                            lineToRelative(-8.0f, -4.0f)
                            lineToRelative(-4.0f, -5.0f)
                            lineToRelative(-4.0f, -7.0f)
                            lineToRelative(-4.0f, -5.0f)
                            lineToRelative(-5.0f, -13.0f)
                            lineToRelative(-3.0f, -8.0f)
                            verticalLineToRelative(-650.0f)
                            lineToRelative(3.0f, -5.0f)
                            lineToRelative(2.0f, -8.0f)
                            lineToRelative(4.0f, -8.0f)
                            lineToRelative(10.0f, -11.0f)
                            lineToRelative(-1.0f, -5.0f)
                            verticalLineToRelative(-3.0f)
                            horizontalLineToRelative(5.0f)
                            lineToRelative(-2.0f, -4.0f)
                            lineToRelative(14.0f, -11.0f)
                            lineToRelative(4.0f, -2.0f)
                            horizontalLineToRelative(7.0f)
                            lineToRelative(2.0f, 1.0f)
                            horizontalLineToRelative(7.0f)
                            close()
                        }
                        path(
                            fill = SolidColor(Color(0xFF030102)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(589.0f, 184.0f)
                            horizontalLineToRelative(29.0f)
                            lineToRelative(3.0f, 2.0f)
                            lineToRelative(12.0f, 2.0f)
                            lineToRelative(19.0f, 9.0f)
                            lineToRelative(8.0f, 5.0f)
                            lineToRelative(10.0f, 9.0f)
                            lineToRelative(7.0f, 7.0f)
                            lineToRelative(7.0f, 11.0f)
                            lineToRelative(4.0f, 5.0f)
                            lineToRelative(2.0f, 9.0f)
                            lineToRelative(3.0f, 7.0f)
                            lineToRelative(2.0f, 6.0f)
                            lineToRelative(1.0f, 1.0f)
                            verticalLineToRelative(33.0f)
                            lineToRelative(-2.0f, 3.0f)
                            lineToRelative(-2.0f, 13.0f)
                            lineToRelative(-5.0f, 9.0f)
                            lineToRelative(-6.0f, 8.0f)
                            lineToRelative(-3.0f, 5.0f)
                            lineToRelative(-11.0f, 12.0f)
                            lineToRelative(-4.0f, 2.0f)
                            verticalLineToRelative(2.0f)
                            lineToRelative(-10.0f, 6.0f)
                            lineToRelative(-16.0f, 8.0f)
                            lineToRelative(-8.0f, 3.0f)
                            lineToRelative(-7.0f, 3.0f)
                            horizontalLineToRelative(-34.0f)
                            lineToRelative(-15.0f, -5.0f)
                            lineToRelative(-24.0f, -12.0f)
                            lineToRelative(-10.0f, -9.0f)
                            lineToRelative(-8.0f, -8.0f)
                            lineToRelative(-6.0f, -10.0f)
                            lineToRelative(-9.0f, -18.0f)
                            lineToRelative(-3.0f, -10.0f)
                            lineToRelative(-2.0f, -7.0f)
                            lineToRelative(1.0f, -28.0f)
                            lineToRelative(3.0f, -7.0f)
                            lineToRelative(4.0f, -10.0f)
                            lineToRelative(8.0f, -16.0f)
                            lineToRelative(4.0f, -6.0f)
                            lineToRelative(17.0f, -17.0f)
                            lineToRelative(19.0f, -10.0f)
                            lineToRelative(11.0f, -4.0f)
                            lineToRelative(9.0f, -1.0f)
                            close()
                        }
                        path(
                            fill = SolidColor(Color(0xFF030102)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(213.0f, 180.0f)
                            horizontalLineToRelative(29.0f)
                            lineToRelative(6.0f, 3.0f)
                            horizontalLineToRelative(7.0f)
                            lineToRelative(21.0f, 10.0f)
                            lineToRelative(8.0f, 5.0f)
                            lineToRelative(10.0f, 9.0f)
                            lineToRelative(7.0f, 7.0f)
                            lineToRelative(7.0f, 11.0f)
                            lineToRelative(4.0f, 5.0f)
                            lineToRelative(2.0f, 9.0f)
                            lineToRelative(3.0f, 7.0f)
                            lineToRelative(2.0f, 6.0f)
                            lineToRelative(1.0f, 1.0f)
                            lineToRelative(1.0f, 19.0f)
                            lineToRelative(-1.0f, 14.0f)
                            lineToRelative(-2.0f, 3.0f)
                            lineToRelative(-2.0f, 13.0f)
                            lineToRelative(-5.0f, 9.0f)
                            lineToRelative(-6.0f, 8.0f)
                            lineToRelative(-4.0f, 7.0f)
                            lineToRelative(-11.0f, 11.0f)
                            lineToRelative(-9.0f, 7.0f)
                            lineToRelative(-17.0f, 9.0f)
                            lineToRelative(-6.0f, 3.0f)
                            lineToRelative(-9.0f, 2.0f)
                            lineToRelative(-3.0f, 2.0f)
                            horizontalLineToRelative(-34.0f)
                            lineToRelative(-8.0f, -3.0f)
                            lineToRelative(-8.0f, -2.0f)
                            lineToRelative(-19.0f, -10.0f)
                            lineToRelative(-7.0f, -4.0f)
                            lineToRelative(-15.0f, -15.0f)
                            lineToRelative(-6.0f, -10.0f)
                            lineToRelative(-9.0f, -18.0f)
                            lineToRelative(-2.0f, -9.0f)
                            lineToRelative(-2.0f, -2.0f)
                            lineToRelative(-1.0f, -11.0f)
                            lineToRelative(1.0f, -22.0f)
                            lineToRelative(3.0f, -8.0f)
                            lineToRelative(4.0f, -10.0f)
                            lineToRelative(10.0f, -20.0f)
                            lineToRelative(19.0f, -19.0f)
                            lineToRelative(26.0f, -13.0f)
                            lineToRelative(13.0f, -2.0f)
                            close()
                        }
                        path(
                            fill = SolidColor(Color(0xFF030102)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(176.0f, 500.0f)
                            horizontalLineToRelative(496.0f)
                            lineToRelative(4.0f, 5.0f)
                            verticalLineToRelative(9.0f)
                            lineToRelative(-4.0f, 5.0f)
                            lineToRelative(-2.0f, 1.0f)
                            horizontalLineToRelative(-476.0f)
                            lineToRelative(-18.0f, -1.0f)
                            lineToRelative(-4.0f, -4.0f)
                            lineToRelative(-1.0f, -7.0f)
                            lineToRelative(3.0f, -6.0f)
                            close()
                        }
                    }
                }
                .build()
        return _midPainIcon!!
    }

private var _midPainIcon: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.MidPainIcon, contentDescription = null)
    }
}
