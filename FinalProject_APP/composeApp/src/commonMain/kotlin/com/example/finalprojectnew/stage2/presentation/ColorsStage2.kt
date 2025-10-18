package com.example.finalprojectnew.stage2.presentation

import androidx.compose.ui.graphics.Color

object Stage2Colors {
    // בסיס
    val ScreenBg    = Color(0xFFFEFAF1)
    val BrandGreen  = Color(0xFF046030)
    val BodyText    = Color(0xFF000000)
    val ButtonText  = Color(0xFFF1FFF6)

    // גוונים ייעודיים למסכים/כפתורים/אריחים
    val FrameGreen  = Color(0xFF0A4E2A) // מסגרות/כותרות משניות
    val TileWhite   = Color(0xFFF8FFF9) // רקע אריחים לבנים-מנטה
    val Mint        = Color(0xFFEFF8F2) // רקע/כפתור מנטה
    val MintTile    = Color(0xFFF0FBF4) // שכבת רקע לאריחי קטגוריות
    val White       = Color(0xFFFFFFFF)

    // === חדשים ===
    /** כחול למסגרות/טקסטים של רכיבי "רשימה לתרומה" (גלולות, אייקונים וכו'). */
    val FrameBlue   = Color(0xFF0F6CBD)

    /** טינט לרקע כרטיסי מוצרים של תרומה – 8CD4FE עם שקיפות 41% */
    val DonationTint = Color(0xFF8CD4FE).copy(alpha = 0.41f)
}
