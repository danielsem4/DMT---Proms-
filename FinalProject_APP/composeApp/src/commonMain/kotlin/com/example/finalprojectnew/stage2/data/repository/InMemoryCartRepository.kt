package com.example.finalprojectnew.stage2.data.repository

import com.example.finalprojectnew.stage2.domain.model.CartItem
import com.example.finalprojectnew.stage2.domain.model.Product
import com.example.finalprojectnew.stage2.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// all the actions of the shopping cart:
// adding, removing, display the items

class InMemoryCartRepository : CartRepository {
    // we save in cart_ the list of items (in the shoppinf cart)
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    // MutableStateFlow= asynchronous data structure that allows us to update the cart.
    // emptyList= the cart start with no items.
    override val cart: Flow<List<CartItem>> = _cart.asStateFlow() // this is the public function that returns the current contents of the shopping cart.

    override suspend fun add(product: Product, qty: Int) { // add new item to the cart
        val current = _cart.value.toMutableList() // turns the fixed list into a list we can change
        val idx = current.indexOfFirst { it.product.id == product.id } // Check if the product is already in the cart
        if (idx >= 0) { // If the product is found - we will update the quantity
            val old = current[idx] // If the product is found, we update its quantity in the cart
            current[idx] = old.copy(quantity = (old.quantity + qty).coerceAtLeast(1)) // Ensures that the quantity is not less than 1 (cannot be a negative item)

        // If the product is not in the cart, we add it to the cart with the selected quantity (which must be at least 1).
        } else {
            current += CartItem(product, qty.coerceAtLeast(1))
        }
        _cart.value = current // Update the new cart_ with the updated value.
    }

    override suspend fun update(productId: String, qty: Int) {
        // Find existing CartItem, and delegate to the Product-based update.
        // If the item is not in the cart, do nothing
        val item = _cart.value.firstOrNull { it.product.id == productId } ?: return
        update(item.product, qty)
    }
    override suspend fun update(product: Product, qty: Int) {
        val current = _cart.value.toMutableList()
        val idx = current.indexOfFirst { it.product.id == product.id } // verify the product
        when {
            qty <= 0 -> if (idx >= 0) current.removeAt(idx)
            idx >= 0 -> current[idx] = current[idx].copy(quantity = qty)
            else -> current += CartItem(product, qty)
        }
        _cart.value = current
    }

    override suspend fun remove(productId: String) {
        _cart.value = _cart.value.filterNot { it.product.id == productId }
    }

    override suspend fun clear() {
        _cart.value = emptyList()
    }
}
