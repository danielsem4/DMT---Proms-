//This file defines the basic data models of the app.
//Ingredient represents a single ingredient with an id and a name, and Recipe represents a full recipe with an id, a name, a key for images, and a list of ingredients.

package com.example.finalprojectnew.domain.model

data class Ingredient(val id: String, val name: String)
data class Recipe(
    val id: String,
    val name: String,        // "פשטידה" / "סלט" / "עוגה" (the recipes name)
    val key: String,         // "pie" / "salad" / "cake" (image identify)
    val ingredients: List<Ingredient>
)
