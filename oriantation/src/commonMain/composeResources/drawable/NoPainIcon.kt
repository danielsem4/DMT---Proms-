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

public val Icons.NoPainIcon: ImageVector
    get() {
        if (_noPainIcon != null) {
            return _noPainIcon!!
        }
        _noPainIcon =
            Builder(
                    name = "NoPainIcon",
                    defaultWidth = 200.0.dp,
                    defaultHeight = 200.0.dp,
                    viewportWidth = 928.0f,
                    viewportHeight = 936.0f,
                )
                .apply {
                    group {
                        path(
                            fill = SolidColor(Color(0xFF78FA80)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(98.0f, 36.0f)
                            horizontalLineToRelative(665.0f)
                            lineToRelative(9.0f, 2.0f)
                            lineToRelative(16.0f, 6.0f)
                            lineToRelative(12.0f, 7.0f)
                            lineToRelative(5.0f, 4.0f)
                            verticalLineToRelative(2.0f)
                            lineToRelative(4.0f, 2.0f)
                            lineToRelative(10.0f, 10.0f)
                            lineToRelative(6.0f, 10.0f)
                            lineToRelative(3.0f, 8.0f)
                            lineToRelative(3.0f, 7.0f)
                            verticalLineToRelative(607.0f)
                            lineToRelative(-1.0f, 46.0f)
                            lineToRelative(-2.0f, 2.0f)
                            lineToRelative(-2.0f, 9.0f)
                            lineToRelative(-6.0f, 11.0f)
                            lineToRelative(-13.0f, 13.0f)
                            lineToRelative(-8.0f, 7.0f)
                            lineToRelative(-16.0f, 9.0f)
                            lineToRelative(-10.0f, 2.0f)
                            lineToRelative(-3.0f, 2.0f)
                            lineToRelative(-15.0f, 1.0f)
                            horizontalLineToRelative(-640.0f)
                            lineToRelative(-18.0f, -1.0f)
                            lineToRelative(-5.0f, -3.0f)
                            lineToRelative(-13.0f, -5.0f)
                            lineToRelative(-13.0f, -10.0f)
                            lineToRelative(-7.0f, -5.0f)
                            lineToRelative(-7.0f, -11.0f)
                            lineToRelative(-5.0f, -8.0f)
                            lineToRelative(-6.0f, -16.0f)
                            lineToRelative(-1.0f, -649.0f)
                            lineToRelative(3.0f, -7.0f)
                            lineToRelative(2.0f, -7.0f)
                            lineToRelative(6.0f, -10.0f)
                            lineToRelative(7.0f, -7.0f)
                            lineToRelative(5.0f, -8.0f)
                            lineToRelative(4.0f, -2.0f)
                            lineToRelative(5.0f, -6.0f)
                            lineToRelative(5.0f, -4.0f)
                            lineToRelative(17.0f, -6.0f)
                            close()
                        }
                        path(
                            fill = SolidColor(Color(0xFF010001)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(233.0f, 535.0f)
                            horizontalLineToRelative(416.0f)
                            lineToRelative(4.0f, 2.0f)
                            lineToRelative(3.0f, 5.0f)
                            lineToRelative(1.0f, 7.0f)
                            lineToRelative(-7.0f, 8.0f)
                            lineToRelative(-11.0f, 7.0f)
                            lineToRelative(-9.0f, 6.0f)
                            lineToRelative(-10.0f, 7.0f)
                            lineToRelative(-14.0f, 9.0f)
                            lineToRelative(-10.0f, 7.0f)
                            lineToRelative(-16.0f, 12.0f)
                            lineToRelative(-18.0f, 12.0f)
                            lineToRelative(-11.0f, 7.0f)
                            lineToRelative(-9.0f, 6.0f)
                            lineToRelative(-11.0f, 7.0f)
                            lineToRelative(-12.0f, 7.0f)
                            lineToRelative(-8.0f, 5.0f)
                            lineToRelative(-15.0f, 8.0f)
                            lineToRelative(-23.0f, 11.0f)
                            lineToRelative(-7.0f, 4.0f)
                            lineToRelative(-8.0f, 1.0f)
                            lineToRelative(-4.0f, 3.0f)
                            lineToRelative(-17.0f, 1.0f)
                            lineToRelative(-2.0f, 3.0f)
                            horizontalLineToRelative(-30.0f)
                            lineToRelative(-3.0f, -3.0f)
                            lineToRelative(-14.0f, -1.0f)
                            lineToRelative(-18.0f, -6.0f)
                            lineToRelative(-13.0f, -5.0f)
                            lineToRelative(-15.0f, -8.0f)
                            lineToRelative(-9.0f, -6.0f)
                            lineToRelative(-11.0f, -6.0f)
                            lineToRelative(-5.0f, -4.0f)
                            lineToRelative(-7.0f, -4.0f)
                            lineToRelative(-5.0f, -4.0f)
                            lineToRelative(-7.0f, -4.0f)
                            lineToRelative(-5.0f, -4.0f)
                            lineToRelative(-7.0f, -4.0f)
                            lineToRelative(-9.0f, -8.0f)
                            lineToRelative(-7.0f, -4.0f)
                            lineToRelative(-11.0f, -11.0f)
                            lineToRelative(-10.0f, -6.0f)
                            lineToRelative(-8.0f, -7.0f)
                            lineToRelative(-9.0f, -6.0f)
                            lineToRelative(-13.0f, -13.0f)
                            lineToRelative(-11.0f, -7.0f)
                            lineToRelative(-9.0f, -9.0f)
                            verticalLineToRelative(-7.0f)
                            lineToRelative(5.0f, -7.0f)
                            close()
                        }
                        path(
                            fill = SolidColor(Color(0xFF010001)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(616.0f, 187.0f)
                            horizontalLineToRelative(10.0f)
                            lineToRelative(10.0f, 1.0f)
                            lineToRelative(3.0f, 3.0f)
                            horizontalLineToRelative(8.0f)
                            lineToRelative(5.0f, 2.0f)
                            lineToRelative(17.0f, 9.0f)
                            lineToRelative(8.0f, 4.0f)
                            lineToRelative(18.0f, 18.0f)
                            lineToRelative(4.0f, 8.0f)
                            lineToRelative(5.0f, 5.0f)
                            lineToRelative(2.0f, 10.0f)
                            lineToRelative(2.0f, 3.0f)
                            lineToRelative(2.0f, 9.0f)
                            lineToRelative(2.0f, 2.0f)
                            verticalLineToRelative(34.0f)
                            lineToRelative(-3.0f, 3.0f)
                            lineToRelative(-1.0f, 12.0f)
                            lineToRelative(-5.0f, 9.0f)
                            lineToRelative(-6.0f, 8.0f)
                            lineToRelative(-4.0f, 7.0f)
                            lineToRelative(-15.0f, 15.0f)
                            lineToRelative(-6.0f, 4.0f)
                            lineToRelative(-21.0f, 10.0f)
                            lineToRelative(-6.0f, 2.0f)
                            lineToRelative(-4.0f, 1.0f)
                            lineToRelative(-2.0f, 2.0f)
                            horizontalLineToRelative(-37.0f)
                            lineToRelative(-5.0f, -3.0f)
                            lineToRelative(-9.0f, -2.0f)
                            lineToRelative(-19.0f, -10.0f)
                            lineToRelative(-9.0f, -6.0f)
                            lineToRelative(-13.0f, -13.0f)
                            lineToRelative(-6.0f, -10.0f)
                            lineToRelative(-8.0f, -17.0f)
                            lineToRelative(-2.0f, -6.0f)
                            lineToRelative(-1.0f, -4.0f)
                            lineToRelative(-2.0f, -1.0f)
                            lineToRelative(-1.0f, -5.0f)
                            verticalLineToRelative(-13.0f)
                            lineToRelative(1.0f, -17.0f)
                            lineToRelative(2.0f, -2.0f)
                            lineToRelative(3.0f, -11.0f)
                            lineToRelative(12.0f, -24.0f)
                            lineToRelative(19.0f, -19.0f)
                            lineToRelative(11.0f, -6.0f)
                            lineToRelative(7.0f, -3.0f)
                            lineToRelative(10.0f, -5.0f)
                            horizontalLineToRelative(10.0f)
                            lineToRelative(3.0f, -3.0f)
                            close()
                        }
                        path(
                            fill = SolidColor(Color(0xFF010001)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(248.0f, 183.0f)
                            horizontalLineToRelative(7.0f)
                            lineToRelative(5.0f, 1.0f)
                            lineToRelative(2.0f, 3.0f)
                            horizontalLineToRelative(9.0f)
                            lineToRelative(5.0f, 2.0f)
                            lineToRelative(17.0f, 9.0f)
                            lineToRelative(8.0f, 4.0f)
                            lineToRelative(19.0f, 19.0f)
                            lineToRelative(3.0f, 7.0f)
                            lineToRelative(5.0f, 6.0f)
                            lineToRelative(2.0f, 9.0f)
                            lineToRelative(2.0f, 3.0f)
                            lineToRelative(2.0f, 9.0f)
                            lineToRelative(2.0f, 2.0f)
                            verticalLineToRelative(33.0f)
                            lineToRelative(-3.0f, 4.0f)
                            lineToRelative(-1.0f, 11.0f)
                            lineToRelative(-5.0f, 10.0f)
                            lineToRelative(-6.0f, 8.0f)
                            lineToRelative(-4.0f, 7.0f)
                            lineToRelative(-15.0f, 15.0f)
                            lineToRelative(-6.0f, 4.0f)
                            lineToRelative(-23.0f, 11.0f)
                            lineToRelative(-9.0f, 3.0f)
                            lineToRelative(-2.0f, 1.0f)
                            horizontalLineToRelative(-37.0f)
                            lineToRelative(-3.0f, -3.0f)
                            horizontalLineToRelative(-6.0f)
                            lineToRelative(-23.0f, -12.0f)
                            lineToRelative(-7.0f, -4.0f)
                            lineToRelative(-15.0f, -15.0f)
                            lineToRelative(-4.0f, -6.0f)
                            lineToRelative(-9.0f, -19.0f)
                            lineToRelative(-2.0f, -2.0f)
                            lineToRelative(-2.0f, -10.0f)
                            lineToRelative(-2.0f, -1.0f)
                            lineToRelative(-1.0f, -7.0f)
                            verticalLineToRelative(-22.0f)
                            lineToRelative(2.0f, -7.0f)
                            lineToRelative(2.0f, -6.0f)
                            lineToRelative(4.0f, -10.0f)
                            lineToRelative(8.0f, -16.0f)
                            lineToRelative(4.0f, -6.0f)
                            lineToRelative(17.0f, -17.0f)
                            lineToRelative(28.0f, -14.0f)
                            horizontalLineToRelative(10.0f)
                            lineToRelative(3.0f, -3.0f)
                            close()
                        }
                    }
                }
                .build()
        return _noPainIcon!!
    }

private var _noPainIcon: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.NoPainIcon, contentDescription = null)
    }
}
