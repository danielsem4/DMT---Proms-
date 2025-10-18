// composeApp/src/commonMain/kotlin/com/example/finalprojectnew/stage2/presentation/cart/CartViewModel.kt
package com.example.finalprojectnew.stage2.presentation.cart

import com.example.finalprojectnew.stage2.domain.model.CartItem
import com.example.finalprojectnew.stage2.domain.usecase.CartUseCases
import com.example.finalprojectnew.assessment.AssessmentService   // 👈 חדש
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CartRowUi(
    val productId: String,
    val title: String,
    val iconKey: String,
    val quantity: Int
)

data class CartUiState(
    val items: List<CartRowUi> = emptyList(),
    val isEmpty: Boolean = true
)

class CartViewModel(
    private val useCases: CartUseCases
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _state = MutableStateFlow(CartUiState())
    val state: StateFlow<CartUiState> = _state.asStateFlow()

    init {
        scope.launch {
            useCases.observe
                .map { domainItems -> domainItems.map { it.toUi() } }
                .collect { uiItems ->
                    _state.value = CartUiState(
                        items = uiItems,
                        isEmpty = uiItems.isEmpty()
                    )
                }
        }
    }

    fun inc(productId: String, currentQty: Int) = scope.launch {
        useCases.update(productId, currentQty + 1)
    }

    fun dec(productId: String, currentQty: Int) = scope.launch {
        val next = (currentQty - 1).coerceAtLeast(1)
        useCases.update(productId, next)
    }

    fun remove(productId: String) = scope.launch { useCases.remove(productId) }

    /** שמירה מאוחדת: שלב 2 + מה שנרשם בשלב 1 (אם קיים) */
    fun saveFinalCart() {
        val currentCart: Map<String, Int> = state.value.items.associate { it.productId to it.quantity }
        scope.launch {
            val path = AssessmentService.saveStage2AndFlush(currentCart)
            println("✅ Combined JSON saved: $path")
        }
    }

    private fun CartItem.toUi(): CartRowUi = CartRowUi(
        productId = product.id,
        title     = product.name,
        iconKey   = product.imageKey,
        quantity  = quantity
    )
}
