package com.example.fashionmarket.data.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val originalPrice: Double? = null,
    val discountPercentage: Int? = null,
    val category: String,
    val imageUrl: String,
    val colors: List<String> = listOf("Black", "White", "Red", "Blue"),
    val sizes: List<String> = listOf("S", "M", "L", "XL"),
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val isNew: Boolean = false,
    val isFavorite: Boolean = false,
    val stock: Int = 10
) {
    val finalPrice: Double
        get() = originalPrice?.let {
            it * (1 - (discountPercentage?.toDouble() ?: 0.0) / 100)
        } ?: price
}

data class Category(
    val id: Int,
    val name: String,
    val icon: String
)

// Sample data
object SampleData {
    val products = listOf(
        Product(
            id = 1,
            name = "Classic White T-Shirt",
            description = "Premium cotton white t-shirt for everyday wear",
            price = 24.99,
            originalPrice = 29.99,
            discountPercentage = 17,
            category = "T-Shirts",
            imageUrl = "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400",
            rating = 4.5,
            reviewCount = 128,
            isNew = true
        ),
        Product(
            id = 2,
            name = "Denim Jacket",
            description = "Vintage style denim jacket with modern fit",
            price = 79.99,
            category = "Jackets",
            imageUrl = "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=400",
            rating = 4.8,
            reviewCount = 256,
            isFavorite = true
        ),
        Product(
            id = 3,
            name = "Summer Dress",
            description = "Floral print summer dress for beach and casual wear",
            price = 49.99,
            originalPrice = 59.99,
            discountPercentage = 17,
            category = "Dresses",
            imageUrl = "https://images.unsplash.com/photo-1567095761054-7a02e69e5c43?w-400",
            rating = 4.3,
            reviewCount = 89,
            isNew = true
        ),
        Product(
            id = 4,
            name = "Sneakers",
            description = "Comfortable running sneakers with cushion technology",
            price = 89.99,
            category = "Shoes",
            imageUrl = "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=400",
            rating = 4.7,
            reviewCount = 312
        ),
        Product(
            id = 5,
            name = "Winter Coat",
            description = "Warm winter coat with waterproof material",
            price = 129.99,
            originalPrice = 159.99,
            discountPercentage = 19,
            category = "Coats",
            imageUrl = "https://images.unsplash.com/photo-1551488831-00ddcb6c6bd3?w=400",
            rating = 4.9,
            reviewCount = 167
        ),
        Product(
            id = 6,
            name = "Casual Pants",
            description = "Comfortable casual pants for office and leisure",
            price = 44.99,
            category = "Pants",
            imageUrl = "https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=400",
            rating = 4.4,
            reviewCount = 94
        )
    )

    val categories = listOf(
        Category(1, "T-Shirts", "👕"),
        Category(2, "Jackets", "🧥"),
        Category(3, "Dresses", "👗"),
        Category(4, "Shoes", "👟"),
        Category(5, "Coats", "🧥"),
        Category(6, "Pants", "👖"),
        Category(7, "Accessories", "🧣"),
        Category(8, "Bags", "👜")
    )
}