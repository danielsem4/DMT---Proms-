package com.example.finalprojectnew.stage1.data.repository

import com.example.finalprojectnew.domain.model.Ingredient
import com.example.finalprojectnew.domain.model.Recipe
import com.example.finalprojectnew.domain.repository.RecipesRepository

class InMemoryRecipesRepository : RecipesRepository {
    private val salad = Recipe(
        id = "salad", name = "סלט", key = "salad",
        ingredients = listOf(
            "עגבניה",
            "מלפפון",
            "מלח",
            "גבינה בולגרית",
            "לימון",
            "באגט",
            "זיתים",
            "שמן זית"
        )
            .mapIndexed { i, n -> Ingredient("salad-$i", n) }
    )
    private val pie = Recipe(
        id = "pie", name = "פשטידה", key = "pie",
        ingredients = listOf(
            "קמח",
            "ביצים",
            "גבינה לבנה",
            "גבינה צהובה",
            "ברוקולי",
            "שמן זית",
            "מלח",
            "פלפל שחור"
        )
            .mapIndexed { i, n -> Ingredient("pie-$i", n) }
    )
    private val cake = Recipe(
        id = "cake", name = "עוגה", key = "cake",
        ingredients = listOf(
            "קקאו",
            "קמח",
            "סוכר",
            "ביצים",
            "שמן",
            "אבקת אפיה",
            "תמצית וניל",
            "שוקולד"
        )
            .mapIndexed { i, n -> Ingredient("cake-$i", n) }
    )
    private val all = listOf(salad, pie, cake)
    override fun getAll() = all  // return all the recipes
    override fun getById(id: String) = all.find { it.id == id } // return recipe by his id
}