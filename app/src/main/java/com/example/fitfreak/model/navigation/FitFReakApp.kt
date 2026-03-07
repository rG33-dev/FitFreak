import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitfreak.presentation.MainScreen
import com.example.fitfreak.calculators.*
import com.example.fitfreak.components.ArticlesScreen
import com.example.fitfreak.components.AssessmentScreen
import com.example.fitfreak.components.ProgressScreen

import com.example.fitfreak.data.AuthViewModel
import com.example.fitfreak.model.navigation.ProgressScreen
import com.example.fitfreak.presentation.LoginScreen
import com.example.fitfreak.presentation.PreviewScreen
import com.example.fitfreak.presentation.RootContainer
import com.example.fitfreak.presentation.SupportScreen
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

    // 1. Get current route to decide if we show BottomBar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 2. Screens that should show the Bottom Navigation
    val bottomNavScreens = listOf("tools_screen", "main_screen", "articles_screen")
    val showBottomBar = currentRoute in bottomNavScreens

    if (showBottomBar) {
        // Main App Layout (With Bottom Bar)
        RootContainer(navController = navController) { modifier ->
            AppNavigation(
                navController = navController,
                authViewModel = authViewModel,
                modifier = modifier
            )
        }
    } else {
        // Auth Layout (No Bottom Bar)
        AppNavigation(
            navController = navController,
            authViewModel = authViewModel,
            modifier = Modifier
        )
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "PreviewScreen",
        modifier = modifier // Important: This handles the BottomBar padding
    ) {
        // --- Auth & Splash ---
        composable("PreviewScreen") {
            PreviewScreen(navController = navController)
        }

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

        // --- Main Content ---
        composable("main_screen") {
            MainScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("assessment_screen") {
            AssessmentScreen(navController = navController)
        }

        composable("support_screen"){
            SupportScreen(navController = navController)
        }


        composable("tools_screen") {
            ToolsScreen( navController = navController)
        }
        composable("progress_screen") { ProgressScreen(navController)}


        composable("articles_screen") { ArticlesScreen() }



        // --- Calculators ---
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