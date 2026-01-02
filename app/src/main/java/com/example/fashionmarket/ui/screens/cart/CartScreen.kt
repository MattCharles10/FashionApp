package com.example.fashionmarket.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fashionmarket.ui.components.FloatingBottomBar
import com.example.fashionmarket.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val cartItems by userViewModel.cartItems.collectAsState()
    val cartTotal = remember(cartItems) { userViewModel.getCartTotal() }
    val cartItemCount = remember(cartItems) { userViewModel.getCartItemCount() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Shopping Cart",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (cartItems.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    userViewModel.clearCart()
                                    snackbarHostState.showSnackbar("Cart cleared")
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Clear Cart",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            Column {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Total:",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "${cartItems.sumOf { it.quantity }} items",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                            Text(
                                text = "$${String.format("%.2f", cartTotal)}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* Handle checkout */ },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            enabled = cartItems.isNotEmpty()
                        ) {
                            Text(
                                text = "Checkout",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }

                // Add FloatingBottomBar for navigation
                FloatingBottomBar(
                    navController = navController,
                    userViewModel = userViewModel,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            EmptyCartView(navController = navController)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cartItems, key = { it.id }) { cartItem ->
                    cartItem.product?.let { product ->
                        CartItemCard(
                            cartItem = cartItem,
                            product = product,
                            onQuantityChange = { newQuantity ->
                                userViewModel.updateCartItemQuantity(cartItem.id, newQuantity)
                            },
                            onRemove = {
                                scope.launch {
                                    userViewModel.removeFromCart(cartItem.id)
                                    snackbarHostState.showSnackbar(
                                        "${product.name} removed from cart"
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: com.example.fashionmarket.data.model.CartItem,
    product: com.example.fashionmarket.data.model.Product,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${product.finalPrice}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (!cartItem.color.isNullOrEmpty() || !cartItem.size.isNullOrEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        cartItem.color?.let { color ->
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        cartItem.size?.let { size ->
                            Text(
                                text = size,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            if (cartItem.quantity > 1) {
                                onQuantityChange(cartItem.quantity - 1)
                            }
                        },
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("-")
                    }

                    Text(
                        text = cartItem.quantity.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    OutlinedButton(
                        onClick = {
                            if (cartItem.quantity < 10) {
                                onQuantityChange(cartItem.quantity + 1)
                            }
                        },
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("+")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = onRemove
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyCartView(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Empty Cart",
            modifier = Modifier.size(100.dp),
            tint = Color.Gray.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Add some products to your cart",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { navController.navigate("home") },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Start Shopping")
        }
    }
}