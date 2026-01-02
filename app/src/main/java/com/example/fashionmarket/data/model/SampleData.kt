package com.example.fashionmarket.data

import com.example.fashionmarket.data.model.*

object SampleData {
    val products = listOf(
        Product(
            id = "1",
            name = "Classic White T-Shirt",
            description = "Premium cotton white t-shirt for everyday wear",
            price = 24.99,
            originalPrice = 29.99,
            discountPercentage = 17,
            category = "T-Shirts",
            imageUrl = "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400",
            images = listOf(
                "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400",
                "https://images.unsplash.com/photo-1581655353564-df123a1eb820?w=400"
            ),
            rating = 4.5,
            reviewCount = 128,
            isNew = true
        ),
        Product(
            id = "2",
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
            id = "3",
            name = "Summer Dress",
            description = "Floral print summer dress for beach and casual wear",
            price = 49.99,
            originalPrice = 59.99,
            discountPercentage = 17,
            category = "Dresses",
            imageUrl = "https://images.unsplash.com/photo-1567095761054-7a02e69e5c43?w=400",
            rating = 4.3,
            reviewCount = 89,
            isNew = true
        ),
        Product(
            id = "4",
            name = "Sneakers",
            description = "Comfortable running sneakers with cushion technology",
            price = 89.99,
            category = "Shoes",
            imageUrl = "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=400",
            rating = 4.7,
            reviewCount = 312,
            isFavorite = true
        ),
        Product(
            id = "5",
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
            id = "6",
            name = "Casual Pants",
            description = "Comfortable casual pants for office and leisure",
            price = 44.99,
            category = "Pants",
            imageUrl = "https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=400",
            rating = 4.4,
            reviewCount = 94
        ),
        Product(
            id = "7",
            name = "Leather Handbag",
            description = "Genuine leather handbag with multiple compartments",
            price = 99.99,
            originalPrice = 129.99,
            discountPercentage = 23,
            category = "Bags",
            imageUrl = "https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=400",
            rating = 4.6,
            reviewCount = 203
        ),
        Product(
            id = "8",
            name = "Sports Watch",
            description = "Waterproof sports watch with heart rate monitor",
            price = 149.99,
            category = "Accessories",
            imageUrl = "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400",
            rating = 4.8,
            reviewCount = 421
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

    val cartItems = listOf(
        CartItem(
            id = "cart_001",
            productId = "1",
            product = products[0],
            quantity = 2,
            size = "M",
            color = "White"
        ),
        CartItem(
            id = "cart_002",
            productId = "2",
            product = products[1],
            quantity = 1,
            size = "L",
            color = "Blue"
        ),
        CartItem(
            id = "cart_003",
            productId = "3",
            product = products[2],
            quantity = 1,
            size = "S",
            color = "Red"
        )
    )

    val orders = listOf(
        Order(
            id = "ORD001",
            products = listOf(
                CartItem("cart_101", "1", product = products[0], quantity = 1),
                CartItem("cart_102", "4", product = products[3], quantity = 1)
            ),
            totalAmount = 114.98,
            orderDate = "2024-01-10",
            status = OrderStatus.DELIVERED,
            shippingAddress = Address(
                id = "addr_001",
                fullName = "Alex Johnson",
                phone = "+1234567890",
                addressLine1 = "123 Main Street",
                city = "New York",
                state = "NY",
                zipCode = "10001",
                country = "USA"
            ),
            paymentMethod = PaymentMethod.CREDIT_CARD
        ),
        Order(
            id = "ORD002",
            products = listOf(
                CartItem("cart_103", "5", product = products[4], quantity = 1)
            ),
            totalAmount = 129.99,
            orderDate = "2024-01-05",
            status = OrderStatus.SHIPPED
        ),
        Order(
            id = "ORD003",
            products = listOf(
                CartItem("cart_104", "2", product = products[1], quantity = 2)
            ),
            totalAmount = 159.98,
            orderDate = "2023-12-20",
            status = OrderStatus.DELIVERED
        )
    )

    fun getProductById(id: String): Product? {
        return products.find { it.id == id }
    }

    fun getProductsByCategory(category: String): List<Product> {
        return products.filter { it.category == category }
    }
}