//The di folder is like a central helper that gives the app what it needs,
// so every screen knows where to get its data from.

package com.example.finalprojectnew.di
import com.example.finalprojectnew.stage1.data.repository.InMemoryRecipesRepository
import com.example.finalprojectnew.domain.repository.RecipesRepository
import com.example.finalprojectnew.domain.usecase.*

object ServiceLocator {
    private val repo: RecipesRepository = InMemoryRecipesRepository()
    val getRecipes = GetRecipes(repo)
    val getRecipeById = GetRecipeById(repo)
}
