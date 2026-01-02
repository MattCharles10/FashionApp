package com.example.fashionmarket.data.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val originalPrice: Double? = null,
    val discountPercentage: Int? = null,
    val category: String,
    val imageUrl: String,
    val images: List<String> = emptyList(),
    val colors: List<String> = listOf("Black", "White", "Red", "Blue"),
    val sizes: List<String> = listOf("S", "M", "L", "XL"),
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val isNew: Boolean = false,
    val isFavorite: Boolean = false,
    val stock: Int = 10,
    val tags: List<String> = emptyList()
) {
    val finalPrice: Double
        get() = originalPrice?.let {
            it * (1 - (discountPercentage?.toDouble() ?: 0.0) / 100)
        } ?: price
}