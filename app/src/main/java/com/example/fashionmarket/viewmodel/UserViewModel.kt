package com.example.fashionmarket.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionmarket.data.SampleData
import com.example.fashionmarket.data.model.CartItem
import com.example.fashionmarket.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    // Use StateFlow for reactive updates
    private val _currentUser = MutableStateFlow(User.createSampleUser())
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    private val _cartItems = MutableStateFlow(SampleData.cartItems)
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _favoriteProducts = MutableStateFlow(SampleData.products.filter { it.isFavorite })
    val favoriteProducts: StateFlow<List<com.example.fashionmarket.data.model.Product>> =
        _favoriteProducts.asStateFlow()

    // Temporary state for login
    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: Boolean
        get() = _isLoggedIn.value

    fun login(email: String, password: String) {
        viewModelScope.launch {
            // Simulate login
            _isLoggedIn.value = true
        }
    }

    fun logout() {
        viewModelScope.launch {
            _isLoggedIn.value = false
            clearCart()
        }
    }

    fun addToCart(productId: String, quantity: Int = 1, size: String? = null, color: String? = null) {
        viewModelScope.launch {
            _cartItems.update { currentItems ->
                val product = SampleData.getProductById(productId)
                val existingItem = currentItems.find { it.productId == productId }

                if (existingItem != null) {
                    currentItems.map {
                        if (it.productId == productId) {
                            it.copy(quantity = it.quantity + quantity)
                        } else it
                    }
                } else {
                    val newItem = CartItem(
                        id = "cart_${System.currentTimeMillis()}",
                        productId = productId,
                        product = product,
                        quantity = quantity,
                        size = size,
                        color = color
                    )
                    currentItems + newItem
                }
            }
        }
    }

    fun removeFromCart(cartItemId: String) {
        viewModelScope.launch {
            _cartItems.update { currentItems ->
                currentItems.filter { it.id != cartItemId }
            }
        }
    }

    fun updateCartItemQuantity(cartItemId: String, quantity: Int) {
        viewModelScope.launch {
            _cartItems.update { currentItems ->
                currentItems.map {
                    if (it.id == cartItemId) it.copy(quantity = quantity) else it
                }
            }
        }
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            _favoriteProducts.update { currentFavorites ->
                val product = SampleData.getProductById(productId)
                val isFavorite = currentFavorites.any { it.id == productId }

                if (isFavorite) {
                    currentFavorites.filter { it.id != productId }
                } else {
                    if (product != null) {
                        currentFavorites + product
                    } else {
                        currentFavorites
                    }
                }
            }
        }
    }

    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.totalPrice }
    }

    fun getCartItemCount(): Int {
        return _cartItems.value.sumOf { it.quantity }
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartItems.update { emptyList() }
        }
    }

    fun getUserOrders() = SampleData.orders
}