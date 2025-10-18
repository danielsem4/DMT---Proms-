
// This file defines small actions (use cases) for recipes:
// one to get all recipes, and one to get a recipe by its ID.
package com.example.finalprojectnew.domain.usecase
import com.example.finalprojectnew.domain.repository.RecipesRepository

class GetRecipes(private val repo: RecipesRepository) {
    operator fun invoke() = repo.getAll()
}
class GetRecipeById(private val repo: RecipesRepository) {
    operator fun invoke(id: String) = repo.getById(id)
}