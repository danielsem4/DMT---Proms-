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

public val Icons.SmallPainIcon: ImageVector
    get() {
        if (_smallPainIcon != null) {
            return _smallPainIcon!!
        }
        _smallPainIcon =
            Builder(
                    name = "SmallPainIcon",
                    defaultWidth = 200.0.dp,
                    defaultHeight = 200.0.dp,
                    viewportWidth = 876.0f,
                    viewportHeight = 848.0f,
                )
                .apply {
                    group {
                        path(
                            fill = SolidColor(Color(0xFF04AC0B)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(89.0f, 32.0f)
                            horizontalLineToRelative(670.0f)
                            lineToRelative(17.0f, 6.0f)
                            lineToRelative(15.0f, 8.0f)
                            lineToRelative(15.0f, 14.0f)
                            lineToRelative(8.0f, 9.0f)
                            lineToRelative(5.0f, 10.0f)
                            lineToRelative(3.0f, 9.0f)
                            lineToRelative(1.0f, 1.0f)
                            lineToRelative(1.0f, 8.0f)
                            verticalLineToRelative(151.0f)
                            lineToRelative(-1.0f, 491.0f)
                            lineToRelative(-3.0f, 7.0f)
                            lineToRelative(-3.0f, 10.0f)
                            lineToRelative(-6.0f, 10.0f)
                            lineToRelative(-5.0f, 6.0f)
                            horizontalLineToRelative(-2.0f)
                            lineToRelative(-2.0f, 4.0f)
                            lineToRelative(-11.0f, 9.0f)
                            lineToRelative(-17.0f, 9.0f)
                            lineToRelative(-11.0f, 3.0f)
                            lineToRelative(-10.0f, 2.0f)
                            horizontalLineToRelative(-663.0f)
                            lineToRelative(-5.0f, -3.0f)
                            lineToRelative(-9.0f, -3.0f)
                            lineToRelative(-10.0f, -6.0f)
                            lineToRelative(-9.0f, -7.0f)
                            lineToRelative(-8.0f, -7.0f)
                            lineToRelative(-6.0f, -9.0f)
                            lineToRelative(-7.0f, -13.0f)
                            lineToRelative(-4.0f, -12.0f)
                            verticalLineToRelative(-649.0f)
                            lineToRelative(3.0f, -5.0f)
                            lineToRelative(2.0f, -8.0f)
                            lineToRelative(5.0f, -9.0f)
                            lineToRelative(9.0f, -10.0f)
                            lineToRelative(2.0f, -6.0f)
                            lineToRelative(7.0f, -3.0f)
                            lineToRelative(5.0f, -5.0f)
                            lineToRelative(3.0f, -1.0f)
                            lineToRelative(1.0f, -3.0f)
                            lineToRelative(10.0f, -4.0f)
                            lineToRelative(9.0f, -3.0f)
                            close()
                        }
                        path(
                            fill = SolidColor(Color(0xFF020202)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(601.0f, 183.0f)
                            horizontalLineToRelative(13.0f)
                            lineToRelative(11.0f, 1.0f)
                            lineToRelative(5.0f, 3.0f)
                            horizontalLineToRelative(7.0f)
                            lineToRelative(12.0f, 5.0f)
                            lineToRelative(19.0f, 10.0f)
                            lineToRelative(15.0f, 15.0f)
                            lineToRelative(5.0f, 6.0f)
                            lineToRelative(7.0f, 11.0f)
                            lineToRelative(8.0f, 23.0f)
                            lineToRelative(1.0f, 5.0f)
                            verticalLineToRelative(26.0f)
                            lineToRelative(-3.0f, 6.0f)
                            lineToRelative(-2.0f, 13.0f)
                            lineToRelative(-8.0f, 13.0f)
                            lineToRelative(-7.0f, 10.0f)
                            lineToRelative(-8.0f, 8.0f)
                            lineToRelative(-5.0f, 6.0f)
                            lineToRelative(-12.0f, 7.0f)
                            lineToRelative(-19.0f, 9.0f)
                            lineToRelative(-9.0f, 3.0f)
                            lineToRelative(-15.0f, 1.0f)
                            horizontalLineToRelative(-14.0f)
                            lineToRelative(-10.0f, -2.0f)
                            lineToRelative(-11.0f, -3.0f)
                            lineToRelative(-23.0f, -12.0f)
                            lineToRelative(-10.0f, -9.0f)
                            lineToRelative(-8.0f, -8.0f)
                            lineToRelative(-8.0f, -14.0f)
                            lineToRelative(-8.0f, -17.0f)
                            lineToRelative(-4.0f, -11.0f)
                            verticalLineToRelative(-29.0f)
                            lineToRelative(7.0f, -19.0f)
                            lineToRelative(10.0f, -19.0f)
                            lineToRelative(9.0f, -10.0f)
                            lineToRelative(9.0f, -9.0f)
                            lineToRelative(21.0f, -11.0f)
                            lineToRelative(8.0f, -3.0f)
                            lineToRelative(9.0f, -1.0f)
                            lineToRelative(4.0f, -3.0f)
                            close()
                        }
                        path(
                            fill = SolidColor(Color(0xFF020202)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(223.0f, 180.0f)
                            horizontalLineToRelative(26.0f)
                            lineToRelative(4.0f, 2.0f)
                            lineToRelative(12.0f, 2.0f)
                            lineToRelative(16.0f, 8.0f)
                            lineToRelative(11.0f, 6.0f)
                            lineToRelative(19.0f, 19.0f)
                            lineToRelative(6.0f, 10.0f)
                            lineToRelative(4.0f, 9.0f)
                            lineToRelative(6.0f, 17.0f)
                            lineToRelative(1.0f, 5.0f)
                            verticalLineToRelative(26.0f)
                            lineToRelative(-3.0f, 6.0f)
                            lineToRelative(-2.0f, 13.0f)
                            lineToRelative(-9.0f, 14.0f)
                            lineToRelative(-8.0f, 12.0f)
                            horizontalLineToRelative(-2.0f)
                            lineToRelative(-2.0f, 4.0f)
                            lineToRelative(-6.0f, 5.0f)
                            lineToRelative(-5.0f, 5.0f)
                            lineToRelative(-16.0f, 8.0f)
                            lineToRelative(-12.0f, 5.0f)
                            lineToRelative(-7.0f, 2.0f)
                            lineToRelative(-5.0f, 2.0f)
                            horizontalLineToRelative(-30.0f)
                            lineToRelative(-10.0f, -4.0f)
                            lineToRelative(-7.0f, -2.0f)
                            lineToRelative(-16.0f, -8.0f)
                            lineToRelative(-10.0f, -6.0f)
                            lineToRelative(-14.0f, -14.0f)
                            lineToRelative(-8.0f, -14.0f)
                            lineToRelative(-9.0f, -20.0f)
                            lineToRelative(-3.0f, -8.0f)
                            verticalLineToRelative(-28.0f)
                            lineToRelative(6.0f, -17.0f)
                            lineToRelative(8.0f, -17.0f)
                            lineToRelative(8.0f, -11.0f)
                            lineToRelative(11.0f, -11.0f)
                            lineToRelative(13.0f, -8.0f)
                            lineToRelative(17.0f, -8.0f)
                            lineToRelative(12.0f, -2.0f)
                            close()
                        }
                        path(
                            fill = SolidColor(Color(0xFF020202)),
                            stroke = null,
                            strokeLineWidth = 0.0f,
                            strokeLineCap = Butt,
                            strokeLineJoin = Miter,
                            strokeLineMiter = 4.0f,
                            pathFillType = NonZero,
                        ) {
                            moveToRelative(206.0f, 508.0f)
                            lineToRelative(8.0f, 1.0f)
                            lineToRelative(24.0f, 10.0f)
                            lineToRelative(16.0f, 7.0f)
                            lineToRelative(13.0f, 5.0f)
                            lineToRelative(27.0f, 11.0f)
                            lineToRelative(20.0f, 8.0f)
                            lineToRelative(36.0f, 12.0f)
                            lineToRelative(29.0f, 7.0f)
                            lineToRelative(2.0f, 1.0f)
                            lineToRelative(9.0f, 1.0f)
                            horizontalLineToRelative(66.0f)
                            lineToRelative(9.0f, -2.0f)
                            lineToRelative(20.0f, -4.0f)
                            lineToRelative(21.0f, -6.0f)
                            lineToRelative(22.0f, -7.0f)
                            lineToRelative(69.0f, -23.0f)
                            lineToRelative(16.0f, -6.0f)
                            lineToRelative(10.0f, -4.0f)
                            lineToRelative(33.0f, -11.0f)
                            horizontalLineToRelative(7.0f)
                            lineToRelative(4.0f, 5.0f)
                            lineToRelative(1.0f, 9.0f)
                            lineToRelative(-5.0f, 5.0f)
                            lineToRelative(-24.0f, 9.0f)
                            lineToRelative(-30.0f, 10.0f)
                            lineToRelative(-17.0f, 7.0f)
                            lineToRelative(-40.0f, 13.0f)
                            lineToRelative(-33.0f, 11.0f)
                            lineToRelative(-28.0f, 7.0f)
                            lineToRelative(-21.0f, 4.0f)
                            lineToRelative(-14.0f, 3.0f)
                            horizontalLineToRelative(-72.0f)
                            lineToRelative(-6.0f, -3.0f)
                            lineToRelative(-14.0f, -1.0f)
                            lineToRelative(-10.0f, -3.0f)
                            lineToRelative(-10.0f, -2.0f)
                            lineToRelative(-27.0f, -9.0f)
                            lineToRelative(-26.0f, -10.0f)
                            lineToRelative(-32.0f, -13.0f)
                            lineToRelative(-10.0f, -5.0f)
                            lineToRelative(-11.0f, -4.0f)
                            lineToRelative(-17.0f, -8.0f)
                            lineToRelative(-17.0f, -7.0f)
                            lineToRelative(-4.0f, -4.0f)
                            verticalLineToRelative(-6.0f)
                            lineToRelative(4.0f, -6.0f)
                            close()
                        }
                    }
                }
                .build()
        return _smallPainIcon!!
    }

private var _smallPainIcon: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.SmallPainIcon, contentDescription = null)
    }
}
