package com.example.fashionmarket.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fashionmarket.ui.navigation.Screen
import com.example.fashionmarket.viewmodel.UserViewModel

// Change from sealed class to data class
data class NavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val badgeCount: Int? = null
)

// Create companion object with default items
object NavItems {
    val Home = NavItem(
        route = Screen.Home.route,
        title = "Home",
        icon = Icons.Default.Home
    )

    val Search = NavItem(
        route = Screen.Products.route,
        title = "Search",
        icon = Icons.Default.Search
    )

    val Cart = NavItem(
        route = Screen.Cart.route,
        title = "Cart",
        icon = Icons.Default.ShoppingCart,
        badgeCount = null
    )

    val Profile = NavItem(
        route = Screen.Profile.route,
        title = "Profile",
        icon = Icons.Default.Person
    )
}

@Composable
fun FloatingBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val cartItems by userViewModel.cartItems.collectAsState()
    val cartItemCount = cartItems.sumOf { it.quantity }

    // Create nav items list with dynamic cart badge
    val navItems = listOf(
        NavItems.Home,
        NavItems.Search,
        // Create cart item with current badge count
        NavItems.Cart.copy(badgeCount = if (cartItemCount > 0) cartItemCount else null),
        NavItems.Profile
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(30.dp),
                clip = true,
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ),
        tonalElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val isSelected = currentDestination?.route == item.route

                FloatingNavItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = {
                        if (currentDestination?.route != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FloatingNavItem(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedIconColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        animationSpec = tween(durationMillis = 200),
        label = "iconColorAnimation"
    )

    val animatedTextColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        animationSpec = tween(durationMillis = 200),
        label = "textColorAnimation"
    )

    Column(
        modifier = Modifier
            .width(70.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Container
        Box(
            modifier = Modifier
                .size(if (isSelected) 46.dp else 38.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    else Color.Transparent
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(20.dp),
                tint = animatedIconColor
            )

            // Badge for cart items
            item.badgeCount?.let { count ->
                if (count > 0) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.error)
                            .align(Alignment.TopEnd)
                            .offset(x = 6.dp, y = (-6).dp)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (count > 9) "9+" else count.toString(),
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Text Label
        Text(
            text = item.title,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = animatedTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}