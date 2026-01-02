
package com.example.fashionmarket.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fashionmarket.R
import com.example.fashionmarket.ui.components.FloatingBottomBar
import com.example.fashionmarket.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val currentUser by userViewModel.currentUser.collectAsState()
    val cartItems by userViewModel.cartItems.collectAsState()
    val favoriteProducts by userViewModel.favoriteProducts.collectAsState()
    val orders = userViewModel.getUserOrders()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {
            FloatingBottomBar(
                navController = navController,
                userViewModel = userViewModel
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                ProfileHeader(currentUser = currentUser)
            }

            item {
                ProfileStats(
                    ordersCount = orders.size,
                    wishlistCount = favoriteProducts.size,
                    cartCount = cartItems.sumOf { it.quantity }
                )
            }

            // Account Section
            item {
                Text(
                    text = "Account",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(accountMenuItems) { menuItem ->
                ProfileMenuItem(
                    icon = menuItem.icon,
                    title = menuItem.title,
                    description = menuItem.description,
                    onClick = { menuItem.action(navController) }
                )
            }

            // Shopping Section
            item {
                Text(
                    text = "Shopping",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(shoppingMenuItems) { menuItem ->
                ProfileMenuItem(
                    icon = menuItem.icon,
                    title = menuItem.title,
                    description = menuItem.description,
                    onClick = { menuItem.action(navController) }
                )
            }

            // Support Section
            item {
                Text(
                    text = "Support",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(supportMenuItems) { menuItem ->
                ProfileMenuItem(
                    icon = menuItem.icon,
                    title = menuItem.title,
                    description = menuItem.description,
                    onClick = { menuItem.action(navController) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                // Logout Button
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            userViewModel.logout()
                            navController.navigate("login") {
                                popUpTo(0)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logout"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }
}

@Composable
fun ProfileHeader(
    currentUser: com.example.fashionmarket.data.model.User
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentUser.profileImage != null) {
            AsyncImage(
                model = currentUser.profileImage,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(24.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = currentUser.fullName,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = currentUser.email,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Member since ${currentUser.joinDate}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun ProfileStats(
    ordersCount: Int,
    wishlistCount: Int,
    cartCount: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ProfileStatItem(
            count = ordersCount.toString(),
            label = "Orders",
            icon = Icons.Default.ShoppingBag
        )
        ProfileStatItem(
            count = wishlistCount.toString(),
            label = "Wishlist",
            icon = Icons.Default.Favorite
        )
        ProfileStatItem(
            count = cartCount.toString(),
            label = "Cart",
            icon = Icons.Default.ShoppingCart
        )
    }
}

@Composable
fun ProfileStatItem(
    count: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = count,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: @Composable () -> Unit,
    title: String,
    description: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(28.dp)
            ) {
                icon()
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (description != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Menu Item Data Classes
data class ProfileMenuItemData(
    val title: String,
    val description: String? = null,
    val icon: @Composable () -> Unit,
    val action: (NavController) -> Unit = {}
)

// Account Menu Items (Using only default icons)
val accountMenuItems = listOf(
    ProfileMenuItemData(
        title = "Personal Information",
        description = "Update your name, email, and phone",
        icon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Personal Info",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("personal_info") }
    ),
    ProfileMenuItemData(
        title = "Shipping Addresses",
        description = "Manage your delivery addresses",
        icon = {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Addresses",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("addresses") }
    ),
    ProfileMenuItemData(
        title = "Payment Methods",
        description = "Add or remove payment cards",
        icon = {
            Icon(
                imageVector = Icons.Default.CreditCard,
                contentDescription = "Payment",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("payments") }
    ),
    ProfileMenuItemData(
        title = "Change Password",
        description = "Update your account password",
        icon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Password",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("change_password") }
    )
)

// Shopping Menu Items (Using only default icons)
val shoppingMenuItems = listOf(
    ProfileMenuItemData(
        title = "My Orders",
        description = "View and track your orders",
        icon = {
            Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = "Orders",
                tint = Color(0xFF4CAF50)
            )
        },
        action = { navController -> navController.navigate("orders") }
    ),
    ProfileMenuItemData(
        title = "Wishlist",
        description = "Your saved favorite items",
        icon = {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Wishlist",
                tint = Color.Red
            )
        },
        action = { navController -> navController.navigate("wishlist") }
    ),
    ProfileMenuItemData(
        title = "Shopping Cart",
        description = "Items ready for checkout",
        icon = {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("cart") }
    ),
    ProfileMenuItemData(
        title = "Order History",
        description = "View past purchases",
        icon = {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = "History",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("order_history") }
    ),
    ProfileMenuItemData(
        title = "Returns & Refunds",
        description = "Manage returns and refunds",
        icon = {
            Icon(
                imageVector = Icons.Default.Refresh, // Changed from AssignmentReturn
                contentDescription = "Returns",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("returns") }
    )
)

// Support Menu Items (Using only default icons)
val supportMenuItems = listOf(
    ProfileMenuItemData(
        title = "Help Center",
        description = "Get help with your account",
        icon = {
            Icon(
                imageVector = Icons.Default.Help,
                contentDescription = "Help",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("help") }
    ),
    ProfileMenuItemData(
        title = "Contact Support",
        description = "Chat or email our support team",
        icon = {
            Icon(
                imageVector = Icons.Default.Email, // Changed from SupportAgent
                contentDescription = "Support",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("contact") }
    ),
    ProfileMenuItemData(
        title = "FAQs",
        description = "Frequently asked questions",
        icon = {
            Icon(
                imageVector = Icons.Default.HelpOutline, // Changed from QuestionAnswer
                contentDescription = "FAQs",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("faqs") }
    ),
    ProfileMenuItemData(
        title = "Settings",
        description = "App preferences and notifications",
        icon = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("settings") }
    ),
    ProfileMenuItemData(
        title = "About App",
        description = "Version 1.0.0 • Fashion Market",
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "About",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        action = { navController -> navController.navigate("about") }
    )
)