package com.example.fashionmarket.data.model

import java.text.SimpleDateFormat
import java.util.*

data class Order(
    val id: String,
    val products: List<CartItem>,
    val totalAmount: Double,
    val orderDate: String,
    val status: OrderStatus,
    val shippingAddress: Address? = null,
    val paymentMethod: PaymentMethod? = null
) {
    val formattedDate: String
        get() {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            return try {
                val date = inputFormat.parse(orderDate)
                outputFormat.format(date!!)
            } catch (e: Exception) {
                orderDate
            }
        }
}

enum class OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED;

    fun getDisplayName(): String {
        return when (this) {
            PENDING -> "Pending"
            PROCESSING -> "Processing"
            SHIPPED -> "Shipped"
            DELIVERED -> "Delivered"
            CANCELLED -> "Cancelled"
        }
    }

    fun getColor(): androidx.compose.ui.graphics.Color {
        return when (this) {
            PENDING -> androidx.compose.ui.graphics.Color.Yellow
            PROCESSING -> androidx.compose.ui.graphics.Color.Blue
            SHIPPED -> androidx.compose.ui.graphics.Color.Green
            DELIVERED -> androidx.compose.ui.graphics.Color(0xFF4CAF50)
            CANCELLED -> androidx.compose.ui.graphics.Color.Red
        }
    }
}

data class Address(
    val id: String,
    val fullName: String,
    val phone: String,
    val addressLine1: String,
    val addressLine2: String? = null,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String,
    val isDefault: Boolean = false
)

enum class PaymentMethod {
    CREDIT_CARD, DEBIT_CARD, PAYPAL, CASH_ON_DELIVERY;

    fun getDisplayName(): String {
        return when (this) {
            CREDIT_CARD -> "Credit Card"
            DEBIT_CARD -> "Debit Card"
            PAYPAL -> "PayPal"
            CASH_ON_DELIVERY -> "Cash on Delivery"
        }
    }
}