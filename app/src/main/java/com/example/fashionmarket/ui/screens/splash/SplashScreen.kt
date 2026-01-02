package com.example.fashionmarket.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fashionmarket.ui.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(
    navController: NavController
) {
    // DARK PINK Color Scheme
    val darkPink1 = Color(0xFF880E4F)     // Deep dark pink
    val darkPink2 = Color(0xFFAD1457)     // Medium dark pink
    val darkPink3 = Color(0xFFC2185B)     // Vibrant dark pink
    val darkPink4 = Color(0xFFD81B60)     // Bright dark pink
    val darkAccent = Color(0xFFEC407A)    // Light accent pink
    val deepMagenta = Color(0xFF4A148C)   // Deep purple-pink
    val whiteAccent = Color(0xFFFFCDD2)   // White-pink accent

    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.8f) }
    val rotation = remember { Animatable(0f) }
    val pulseScale = remember { Animatable(1f) }
    val circleProgress = remember { Animatable(0f) }
    val shimmerOffset = remember { Animatable(-1f) }
    val particles = remember { mutableStateListOf<Pair<Float, Float>>() }
    val particleAlpha = remember { Animatable(0f) }

    // Initialize particle positions
    LaunchedEffect(Unit) {
        repeat(15) {
            particles.add(Pair((0..100).random().toFloat() / 100, (0..100).random().toFloat() / 100))
        }
    }

    LaunchedEffect(key1 = true) {
        launch {
            // Fade in
            alpha.animateTo(1f, tween(800, easing = LinearEasing))

            // Scale animation
            launch {
                scale.animateTo(1.1f, tween(400, easing = FastOutSlowInEasing))
                scale.animateTo(1f, tween(300, easing = FastOutSlowInEasing))
            }

            // Circle progress
            launch {
                circleProgress.animateTo(1f, tween(1200, easing = LinearEasing))
            }

            // Particles fade in
            launch {
                particleAlpha.animateTo(1f, tween(500, delayMillis = 300))
            }

            // Infinite rotation
            launch {
                while (true) {
                    rotation.animateTo(360f, tween(2000, easing = LinearEasing))
                    rotation.snapTo(0f)
                }
            }

            // Pulse animation
            launch {
                while (true) {
                    pulseScale.animateTo(1.05f, tween(800, easing = FastOutSlowInEasing))
                    pulseScale.animateTo(1f, tween(800, easing = FastOutSlowInEasing))
                }
            }

            // Shimmer animation
            launch {
                while (true) {
                    shimmerOffset.animateTo(2f, tween(1500, easing = LinearEasing))
                    shimmerOffset.snapTo(-1f)
                }
            }

            delay(2500) // Show for 2.5 seconds

            // Fade out
            alpha.animateTo(0f, tween(500, easing = LinearEasing))
            delay(500)

            // Navigate to Login
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    // Dark pink gradient background
    val gradientBrush = Brush.radialGradient(
        colors = listOf(deepMagenta, darkPink1, darkPink2),
        center = Offset(0.5f, 0.5f),
        radius = 1.0f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        // Animated background elements
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Animated circles
            for (i in 0..4) {
                val radius = 80f + i * 50f * circleProgress.value
                val alphaVal = 0.2f - i * 0.04f
                drawCircle(
                    color = darkAccent.copy(alpha = alphaVal),
                    radius = radius,
                    center = center,
                    style = Stroke(width = 1.5f)
                )
            }

            // Particles
            if (particleAlpha.value > 0) {
                particles.forEach { (x, y) ->
                    val pos = Offset(size.width * x, size.height * y)
                    drawCircle(
                        color = whiteAccent.copy(alpha = particleAlpha.value * 0.6f),
                        radius = 4f,
                        center = pos
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .alpha(alpha.value)
                .fillMaxWidth()
        ) {
            // Main circular container
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Rotating circle
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(rotation.value)
                ) {
                    drawCircle(
                        color = whiteAccent.copy(alpha = 0.3f),
                        radius = size.minDimension / 2 - 10,
                        style = Stroke(width = 3f)
                    )
                }

                // Main icon container with gradient
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(darkPink3, darkPink4, darkAccent)
                            )
                        )
                        .scale(pulseScale.value)
                        .blur(if (pulseScale.value > 1f) 4.dp else 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Shimmer effect
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val shimmerWidth = size.width * 0.3f
                        val shimmerX = size.width * shimmerOffset.value

                        drawRect(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    whiteAccent.copy(alpha = 0.3f),
                                    Color.Transparent
                                ),
                                startX = shimmerX,
                                endX = shimmerX + shimmerWidth
                            ),
                            topLeft = Offset(shimmerX, 0f),
                            size = androidx.compose.ui.geometry.Size(shimmerWidth, size.height)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = "Fashion Market",
                        modifier = Modifier
                            .size(80.dp)
                            .scale(scale.value),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Text content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha.value)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = Color.White,
                                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                        ) {
                            append("FASHION")
                        }
                        append("\n")
                        withStyle(
                            SpanStyle(
                                color = whiteAccent,
                                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                        ) {
                            append("MARKET")
                        }
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Dark Pink Elegance",
                    style = MaterialTheme.typography.headlineSmall,
                    color = whiteAccent.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(pulseScale.value)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Decorative line
                Box(
                    modifier = Modifier
                        .height(3.dp)
                        .width(120.dp * circleProgress.value)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    whiteAccent,
                                    Color.Transparent
                                )
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Loading dots
            Row(
                modifier = Modifier.height(20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(
                                whiteAccent.copy(
                                    alpha = (alpha.value * (0.4f + (pulseScale.value - 1f) * 0.6f)).coerceIn(0f, 1f)
                                )
                            )
                            .scale(
                                scale = 1f + (pulseScale.value - 1f) * if (index == 1) 1f else 0.5f
                            )
                    )
                }
            }
        }
    }
}