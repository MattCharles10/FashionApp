package com.example.fashionmarket.data.model

import com.example.fashionmarket.data.SampleData

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
) {
    companion object {
        fun createSampleUser(): User {
            return User(
                id = "user_001",
                fullName = "Alex Johnson",
                email = "alex.johnson@email.com",
                phone = "+1234567890",
                profileImage = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400",
                joinDate = "2024-01-15",
                cartItems = SampleData.cartItems,
                favoriteProducts = listOf("2", "4"),
                orderHistory = SampleData.orders
            )
        }
    }
}