// Domain contract for the shopping cart.
// UI/ViewModels depend on this interface, not on specific implementations.

package com.example.finalprojectnew.stage2.domain.repository

import com.example.finalprojectnew.stage2.domain.model.CartItem
import com.example.finalprojectnew.stage2.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    val cart: Flow<List<CartItem>> // view the cart
    suspend fun add(product: Product, qty: Int)
    suspend fun update(productId: String, qty: Int)
    suspend fun update(product: Product, qty: Int)
    suspend fun remove(productId: String)
    suspend fun clear()
}
