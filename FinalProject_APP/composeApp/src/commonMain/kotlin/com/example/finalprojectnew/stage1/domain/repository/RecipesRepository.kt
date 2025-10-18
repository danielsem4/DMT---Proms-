//This file defines the rules for how recipes can be fetched, without caring about how the data is actually stored.

package com.example.finalprojectnew.domain.repository
import com.example.finalprojectnew.domain.model.*

interface RecipesRepository {
    fun getAll(): List<Recipe> // get all recipes
    fun getById(id: String): Recipe? // ger recipe by id
}
