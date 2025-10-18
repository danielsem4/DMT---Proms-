package com.example.finalprojectnew.stage2.data.catalog

import com.example.finalprojectnew.stage2.domain.model.CategoryKey
import com.example.finalprojectnew.stage2.domain.model.Product
import com.example.finalprojectnew.stage2.presentation.category.generic.ProductUi

// Convert the ProductUi model to a Product domain.

fun ProductUi.toDomain(category: CategoryKey): Product =
    Product(
        id         = id,
        name       = title,
        categoryId = category.id,
        imageKey   = id
    )
