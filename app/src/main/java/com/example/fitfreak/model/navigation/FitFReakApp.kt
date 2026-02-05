package com.example.fitfreak.model.navigation

import BMICalculatorScreen
import CaloriesCalculatorScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitfreak.calculators.*
import com.example.fitfreak.presentation.MainScreen
import com.example.fitfreak.ui.theme.FitFreakTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitFreakTheme {
                // This is the main app container
                FitFreakApp()
            }
        }
    }
}

@Composable
fun FitFreakApp() {
    // 1. Create the NavController here
    val navController = rememberNavController()

    // 2. NavHost defines the navigation graph
    NavHost(
        navController = navController,
        startDestination = "main_screen" // The first screen to show
    ) {
        // --- Define all your screens here ---

        // The Main Dashboard Screen
        composable("main_screen") {
            // 3. Pass the created navController to your MainScreen
            MainScreen(navController = navController)
        }

        // Add a composable route for each calculator screen
        composable("bmi") { BMICalculatorScreen() }
        composable("calories") { CaloriesCalculatorScreen() }
        composable("endurance") { EnduranceLevelScreen() }
        composable("pr") { PRCalculatorScreen() }
        composable("fitness_level") { FitnessLevelScreen() }
        composable("max_muscle") { MaxMuscleCalculatorScreen() }
        composable("strength_lvl") { StrengthLevelScreen() }
        composable("sexual_health") { SexualHealthScreen() }

        // Add more screens here as you build them
    }
}