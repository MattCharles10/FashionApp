package com.example.fashionmarket.data.model

data class CartItem(
    val id: String,
    val productId: String,
    val product: Product? = null,
    val quantity: Int,
    val size: String? = null,
    val color: String? = null,
    val addedAt: Long = System.currentTimeMillis()
) {
    val totalPrice: Double
        get() = (product?.finalPrice ?: 0.0) * quantity
}