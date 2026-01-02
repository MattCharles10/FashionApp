package com.example.fashionmarket.data.model

data class User(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val profileImage: String? = null,
    val joinDate: String = "",
    val cartItems: List<CartItem> = emptyList(),
    val favoriteProducts: List<String> = emptyList(),
    val orderHistory: List<Order> = emptyList()
)

data class CartItem(
    val productId: String,
    val quantity: Int,
    val size: String? = null,
    val color: String? = null
)

data class Order(
    val id: String,
    val products: List<CartItem>,
    val totalAmount: Double,
    val orderDate: String,
    val status: OrderStatus
)

enum class OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}