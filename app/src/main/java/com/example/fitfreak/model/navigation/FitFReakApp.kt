import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitfreak.AddOns.calculators.*
import com.example.fitfreak.AddOns.components.*
import com.example.fitfreak.model.viewModel.AuthViewModel
import com.example.fitfreak.model.viewModel.FitnessViewModel2
import com.example.fitfreak.presentation.MainScreens.*
import com.example.fitfreak.presentation.UserScreens.LoginScreen

@Composable
fun FitFreakApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()


    val user by authViewModel.authState

    LaunchedEffect(user) {
        if (user == null) {
            navController.navigate("login_screen") {
                popUpTo(0) { inclusive = true }
            }

        }
        else{
            navController.navigate("main_screen")
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavScreens =
        listOf("tools_screen", "main_screen", "articles_screen", "support_screen")
    val showBottomBar = currentRoute in bottomNavScreens

    if (showBottomBar) {
        RootContainer(navController = navController) { modifier ->
            AppNavigation(
                navController = navController,
                authViewModel = authViewModel,
                modifier = modifier
            )
        }
    } else {
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

    val fitnessViewModel: FitnessViewModel2 = viewModel()

    NavHost(
        navController = navController,
        startDestination = "PreviewScreen",
        modifier = modifier
    ) {
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

        composable("main_screen") {
            MainScreen(navController = navController, authViewModel = authViewModel)
        }

        composable("assessment_screen") {

            AssessmentScreen(navController = navController, viewModel = fitnessViewModel)
        }

        composable("progress_screen") {
            ProgressScreen(navController= navController,viewModel2 = fitnessViewModel)
        }

        composable("tools_screen") { ToolsScreen(navController = navController) }
        composable("articles_screen") { ArticlesScreen() }
        composable("support_screen") { SupportScreen(navController = navController) }

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