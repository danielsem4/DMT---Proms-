// the shopping cart items
package com.example.finalprojectnew.stage2.domain.model

data class ShoppingCategory(
    val title: String,
    val items: List<String>
)

val shoppingCategories = listOf(
    ShoppingCategory(
        title = "ירקות",
        items = listOf(
            "קילו עגבניות",
            "חצי קילו מלפפון",
            "ברוקולי טרי"
        )
    ),
    ShoppingCategory(
        title = "מאפים",
        items = listOf(
            "כיכר לחם מקמח מלא דל סוכר",
            "חבילת עוגיות ללא גלוטן"
        )
    ),
    ShoppingCategory(
        title = "יבשים ותבלינים",
        items = listOf(
            "שמן זית",
            "שמן חמניות",
            "תירס שימורים 3 יחידות",
            "מלפפון חמוץ בחומץ 250 גר'",
            "טונה"
        )
    ),
    ShoppingCategory(
        title = "מוצרי בשר",
        items = listOf(
            "קילו שניצלים",
            "כרעיים 4 יחידות"
        )
    ),
    ShoppingCategory(
        title = "פירות",
        items = listOf(
            "10 יחידות משמשים",
            "אשכול בננות עד 700 גר'",
            "5 תפוחים סמיט",
            "מלון 1 יחידה"
        )
    ),
    ShoppingCategory(
        title = "מוצרי חלב",
        items = listOf(
            "ליטר חלב 1% שומן",
            "גבינת שמנת 28%",
            "250 גר גבינה לבנה",
            "250 גר צפתית מעודנת 5%"
        )
    )
)
