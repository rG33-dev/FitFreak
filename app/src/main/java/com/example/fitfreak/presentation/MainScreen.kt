package com.example.fitfreak.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.fitfreak.R

// Data class to define our Menu Items
data class CalculatorMenu(
    val title: String,
    val description: String,
    val route: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val menuItems = listOf(
        CalculatorMenu("BMI Calculator", "Check your body mass index", "bmi", Color(0xFF6200EE)),
        CalculatorMenu("Calorie Tracker", "Bulk, Cut, or Maintain", "calories", Color(0xFF03DAC5)),
        CalculatorMenu("Endurance Level", "Cooper Test & VO2 Max", "endurance", Color(0xFF3700B3)),
        CalculatorMenu("PR Calculator", "1-Rep Max for Compound Lifts", "pr", Color(0xFFCF6679)),
        CalculatorMenu(
            "Fitness Rank",
            "Overall health assessment",
            "fitness_level",
            Color(0xFF4CAF50)
        ),
        CalculatorMenu(
            "Genetic Potential",
            "Max natural muscle mass",
            "max_muscle",
            Color(0xFFFF9800)
        ),
        CalculatorMenu(
            "Strength Level",
            "Nippard Scale (Rookie to Adv)",
            "strength_lvl",
            Color(0xFF2196F3)
        ),
        CalculatorMenu(
            "Sexual Wellness",
            "Libido & Vitality Score",
            "sexual_health",
            Color(0xFFE91E63)
        )
    )

    // Lottie Header Animation
   val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.kick))

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text("FitFreak", fontWeight = FontWeight.Black)
                        Text("Ultimate Fitness Suite", style = MaterialTheme.typography.labelSmall)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header with Lottie
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(150.dp)
                    )
                }
            }

            item {
                Text(
                    "Calculators & Tools",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // List of Navigation Buttons
            items(menuItems) { menu ->
                CalculatorRowItem(menu) {
                    navController.navigate(menu.route)
                }
            }

            // Example Payment Trigger Button (Razorpay Integration spot)
            item {
                Button(
                    onClick = { /* Trigger Razorpay Checkout */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Unlock Pro Features")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorRowItem(menu: CalculatorMenu, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = menu.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = menu.color
                )
                Text(
                    text = menu.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = menu.color
            )
        }
    }
}