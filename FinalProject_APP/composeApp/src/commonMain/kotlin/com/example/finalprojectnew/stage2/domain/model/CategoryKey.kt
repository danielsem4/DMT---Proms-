// List of categories
package com.example.finalprojectnew.stage2.domain.model

enum class CategoryKey(val id: String) {
    MILK("milk"),
    MEAT("meat"),
    VEGETABLES("vegetables"),
    FRUITS("fruits"),
    FROZEN("frozen"),
    DRY("dry"),
    PASTRIES("pastries"),
    CLEANING("cleaning");

    companion object {
        fun fromId(id: String): CategoryKey? = entries.firstOrNull { it.id == id }
    }
}
