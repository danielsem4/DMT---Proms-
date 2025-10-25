// the brain of cart screen
package com.example.finalprojectnew.stage2.presentation.cart

import com.example.finalprojectnew.stage2.domain.model.CartItem
import com.example.finalprojectnew.stage2.domain.usecase.CartUseCases
import com.example.finalprojectnew.assessment.AssessmentService
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
//SupervisorJob – If one coroutine fails, it does not crash others.

    private val _state = MutableStateFlow(CartUiState()) // _state= private
    val state: StateFlow<CartUiState> = _state.asStateFlow() //  state= public only for reading

    init {
        scope.launch {
            useCases.observe
                .map { domainItems -> domainItems.map { it.toUi() } } // convert CartItem to CartRowUI
                .collect { uiItems -> // any update - updating the state_
                    _state.value = CartUiState(
                        items = uiItems,
                        isEmpty = uiItems.isEmpty()
                    )
                }
        }
    }

    // Increase item quantity: Triggers the use case to update quantity. Runs in a coroutine.
    fun inc(productId: String, currentQty: Int) = scope.launch {
        useCases.update(productId, currentQty + 1)
    }

    // Reducing quantity — does not allow it to go below 1
    fun dec(productId: String, currentQty: Int) = scope.launch {
        val next = (currentQty - 1).coerceAtLeast(1)
        useCases.update(productId, next)
    }

    fun remove(productId: String) = scope.launch { useCases.remove(productId) } // delete item

    // after stage2 - AssessmentService save the results of stage1+stage2
    fun saveFinalCart() {
        val currentCart: Map<String, Int> =
            state.value.items.associate { it.productId to it.quantity }
        scope.launch {
            val path = AssessmentService.saveStage2AndFlush(currentCart)
        }
    }

    private fun CartItem.toUi(): CartRowUi = CartRowUi(
        productId = product.id,
        title = product.name,
        iconKey = product.imageKey,
        quantity = quantity
    )
}
