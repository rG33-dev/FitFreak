package com.example.fitfreak.model.navigation

import BMICalculatorScreen
import CaloriesCalculatorScreen
import SignUpScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitfreak.calculators.*
import com.example.fitfreak.data.AuthViewModel
import com.example.fitfreak.presentation.*
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
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()


    // Wrapper for Global Dark Theme and common UI
    FitFreakContainer {
        NavHost(
            navController = navController,
            startDestination = "PreviewScreen"
        ) {
            // 1. Splash / Preview Logic
            composable("PreviewScreen") {
                PreviewScreen(navController = navController)
            }

            // 2. Auth Flow
            composable("signup_screen") {
                SignUpScreen(
                    onNavigateToLogin = {
                        navController.navigate("login_screen")

                    },
                    onSignUpSuccess = {
                        navController.navigate("main_screen") {
                            popUpTo("signup_screen") { inclusive = true }
                        }
                    },
                    authViewModel = authViewModel // this made my cry for 2hrs straight
                )
            }


            composable("login_screen") {
                LoginScreen(
                    onNavigateToSignUp = {
                        navController.navigate("signup_screen")
                    },
                    onLoginSuccess = {
                        navController.navigate("main_screen") {
                            popUpTo("login_screen") { inclusive = true }
                        }
                    },
                    authViewModel = authViewModel
                )
            }

            // 3. Main Content
            composable("main_screen") {
                MainScreen(navController = navController, authViewModel = authViewModel)
            }

            // 4. Calculators
            composable("bmi") { BMICalculatorScreen() }
            composable("calories") { CaloriesCalculatorScreen() }
            composable("endurance") { EnduranceLevelScreen() }
            composable("pr") { PRCalculatorScreen() }
            composable("fitness_level") { FitnessLevelScreen() }
            composable("max_muscle") { MaxMuscleCalculatorScreen() }
            composable("strength_lvl") { StrengthLevelScreen() }
            composable("sexual_health") { SexualHealthScreen() }

            // New "How to Improve" Screen
           // composable("how_to_improve") { HowToImproveScreen() }
        }
    }
}

