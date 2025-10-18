package com.example.finalprojectnew.stage2.domain.model

data class CartItem( // CartItem = product + quantity, as a pure domain object.
    val product: Product,
    val quantity: Int
)
