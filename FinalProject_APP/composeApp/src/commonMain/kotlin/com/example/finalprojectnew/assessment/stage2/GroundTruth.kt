package com.example.finalprojectnew.assessment.stage2

// The ground truth list: shopping cart + donation cart
object GroundTruth {
    val shoppingExpected = mapOf(
        "tomato" to 2,
        "broccoliFrozen" to 1,
        "cookies_gf" to 1,
        "sunflower_oil" to 1,
        "pickles_vinegar" to 1,
        "shnitzelKg" to 1,
        "apricot" to 1,
        "greenApple" to 1,
        "milk1p" to 1,
        "white250" to 1,
        "cucumber" to 1,
        "nosugar_bread" to 1,
        "olive_oil" to 1,
        "canned_corn" to 3,
        "tuna" to 1,
        "legs4" to 1,
        "banana" to -1,
        "melon" to 1,
        "creamCheese28" to 1,
        "bulgarian250" to 1,

        "bleach" to 1,
        "window_cleaner" to 1
    )
    val donationExpected = mapOf(
        "milk3p" to 1,
        "challah" to 1,
        "canola_oil" to 1,
        "olives_canned" to 1,
        "clean_cups" to 1,
        "dry_tomatopaste" to 1,
        "dry_hummus" to 1,
        "dish_soap" to 1
    )

    // Checking if item is considered correct according to the rules
    fun isCorrect(id: String, qty: Int, actual: Map<String, Int>): Boolean {

        if (id == "banana") return qty in 1..7

        val expected = shoppingExpected[id] ?: donationExpected[id] ?: return false
        return qty == expected
    }
}
