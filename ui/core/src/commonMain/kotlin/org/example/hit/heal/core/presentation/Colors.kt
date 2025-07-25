package org.example.hit.heal.core.presentation

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Colors used in the application.
 * These colors are used throughout the app for various UI elements.
 */


val primaryColor = Color(0xFF008EAE)
val backgroundColor = Color(0xFFf0f9ff)

val GrayLighter = Color(0xFFFAFAFA)
val Gray = Color(0xFFF1F1F1)
val GrayDarker = Color(0xFFEBEBEB)

val Yellowish = Color(0xFFEEFF00)
val Orange = Color(0xFFF24C00)
val Green = Color(0xFF00B300)
val White = Color(0xFFFFFFFF)
val LightWhite = Color(0x43FCFCFC)
val Black = Color(0xFF000000)
val Red = Color(0xFFDD0000)
val OffWhite = Color(0xFFF8F8F8)

val Surface = White
val SurfaceLighter = GrayLighter
val SurfaceDarker = Gray
val SurfaceBrand = primaryColor
val SurfaceError = Red

val TextPrimary = Black
val TextSecondary = primaryColor
val TextWhite = White
val TextBrand = primaryColor

val ButtonPrimary = primaryColor
val ButtonRed = Red
val ButtonDisabled = GrayDarker

val IconPrimary = primaryColor
val IconSecondary = Black
val IconWhite = White

val calculatorColor = Color(0xFF8BC34A)
val settingsColor = Color(0xFF616161)
val cameraColor = Color(0xFF90A4AE)
val emailColor = Color(0xFFFFB74D)
val storeColor = Color(0xFFEF5350)
val clockColor = Color(0xFF64B5F6)
val contactsColor = Color(0xFF616161)
val messagesColor = Color(0xFF7986CB)
val purseColor = Color(0xFFBA68C8)
val weatherColor = Color(0xFFFFF176)
val documentsColor = Color(0xFFFFF59D)
val phoneColor = primaryColor

val cardColor = LightWhite

val LightColors = lightColorScheme(
    primary = primaryColor,
    onPrimary = TextWhite,
    primaryContainer = SurfaceBrand,
    onPrimaryContainer = TextWhite,

    secondary = TextSecondary,
    onSecondary = TextWhite,
    secondaryContainer = Surface,

    background = backgroundColor,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,

    error = SurfaceError,
    onError = TextWhite,

    // optional—if you want more control:
    outline = GrayDarker,
    surfaceVariant = SurfaceLighter,
    onSurfaceVariant = TextSecondary,
)

val DarkColors = darkColorScheme(
    primary = primaryColor,
    onPrimary = TextWhite,
    secondary = TextWhite,
    onSecondary = TextPrimary,
    background = Black,
    onBackground = TextWhite,
    surface = GrayDarker,
    onSurface = TextWhite,
    error = SurfaceError,
    onError = TextWhite
)