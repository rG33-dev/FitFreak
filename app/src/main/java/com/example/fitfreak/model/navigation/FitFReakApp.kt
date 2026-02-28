package com.example.fitfreak.presentation
import BMICalculatorScreen
import CaloriesCalculatorScreen
import EnduranceLevelScreen
import MainScreen
import SignUpScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitfreak.calculators.FitnessLevelScreen
import com.example.fitfreak.calculators.MaxMuscleCalculatorScreen
import com.example.fitfreak.calculators.PRCalculatorScreen
import com.example.fitfreak.calculators.SexualHealthScreen
import com.example.fitfreak.calculators.StrengthLevelScreen
import com.example.fitfreak.components.ToolsScreen
import com.example.fitfreak.data.AuthViewModel
import com.example.fitfreak.ui.theme.FitFreakTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitFreakTheme {
                FitFreakApp()
            }
        }
    }
}

@Composable
fun FitFreakApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    // Get current route to decide if we should show the Bottom Bar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // List of screens where we DO NOT want the bottom navigation
    val authScreens = listOf("PreviewScreen", "signup_screen", "login_screen")

    if (currentRoute in authScreens) {
        // Simple NavHost without BottomBar for Auth/Splash
        NavHost(
            navController = navController,
            startDestination = "PreviewScreen"
        ) {
            composable("PreviewScreen") { PreviewScreen(navController = navController) }

            composable("signup_screen") {
                SignUpScreen(
                    onNavigateToLogin = { navController.navigate("login_screen") },
                    onSignUpSuccess = {
                        navController.navigate("main_screen") {
                            popUpTo("signup_screen") { inclusive = true }
                        }
                    },
                    authViewModel = authViewModel
                )
            }

            composable("login_screen") {
                LoginScreen(
                    onNavigateToSignUp = { navController.navigate("signup_screen") },
                    onLoginSuccess = {
                        navController.navigate("main_screen") {
                            popUpTo("login_screen") { inclusive = true }
                        }
                    },
                    authViewModel = authViewModel
                )
            }

            // Allow navigation to main_screen from here
            composable("main_screen") { MainContentWithBottomNav(navController, authViewModel) }
        }
    } else {
        // Screens with Bottom Navigation
        MainContentWithBottomNav(navController, authViewModel)
    }
}

@Composable
fun MainContentWithBottomNav(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    RootContainer(navController = navController) { modifier ->
        NavHost(
            navController = navController,
            startDestination = "PreviewScreen",
            modifier = modifier
        )

        {
            composable("main_screen") {
                MainScreen(navController = navController, authViewModel = authViewModel)
            }

            // Tools Screen
            composable("tools_screen") {
                ToolsScreen()
            }

            // Calculators
            composable("bmi") { BMICalculatorScreen() }
            composable("calories") { CaloriesCalculatorScreen() }
            composable("endurance") { EnduranceLevelScreen() }
            composable("pr") { PRCalculatorScreen() }
            composable("fitness_level") { FitnessLevelScreen() }
            composable("max_muscle") { MaxMuscleCalculatorScreen() }
            composable("strength_lvl") { StrengthLevelScreen() }
            composable("sexual_health") { SexualHealthScreen() }
        }
    }
}




