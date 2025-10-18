package com.example.finalprojectnew.stage2.domain.model

data class SimpleCategory(
    val title: String,
    val items: List<String>
)

// the donation list
val donationCategories: List<SimpleCategory> = listOf(
    SimpleCategory(
        title = "מוצרי מזון",
        items = listOf(
            "חלה",
            "רסק עגבניות",
            "שמן קנולה",
            "חלב 3% שומן",
            "זיתים",
            "חומוס גרגרים",
            "חבילת כוסות חד פעמי",
            "סבון כלים"
        )
    )
)
val donationListItems: List<String> = donationCategories.flatMap { it.items }

val donationItemIds: Set<String> = setOf(
    "challah",
    "canola_oil",
    "olives_canned",
    "clean_cups",
    "dry_tomatopaste",
    "milk3p",
    "dry_hummus",
    "dish_soap"
)
