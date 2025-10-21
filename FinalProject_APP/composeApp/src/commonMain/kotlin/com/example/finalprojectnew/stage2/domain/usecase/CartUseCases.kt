// Centralizes cart operations and transfers them to CartRepository.
package com.example.finalprojectnew.stage2.domain.usecase

import com.example.finalprojectnew.stage2.domain.model.Product
import com.example.finalprojectnew.stage2.domain.repository.CartRepository

class CartUseCases(private val cart: CartRepository) {
    val observe = cart.cart

    suspend fun add(product: Product, qty: Int = 1) = cart.add(product, qty)
    suspend fun update(productId: String, qty: Int) = cart.update(productId, qty)
    suspend fun update(product: Product, qty: Int) = cart.update(product, qty)
    suspend fun remove(productId: String) = cart.remove(productId)
    suspend fun clear() = cart.clear()
}
