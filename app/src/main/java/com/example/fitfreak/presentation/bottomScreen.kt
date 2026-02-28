package com.example.fitfreak.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


sealed class ThisScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : ThisScreen("main_screen", "Home", Icons.Default.Home)
    object Tools : ThisScreen("tools_screen", "Tools", Icons.Default.Build)
    object Articles : ThisScreen("articles_screen", "Articles", Icons.Default.Article)
}
@Composable
fun RootContainer(navController: NavHostController, function: @Composable (Modifier) -> Unit) {
    val items = listOf(ThisScreen.Home, ThisScreen.Tools, ThisScreen.Articles)

    Scaffold(bottomBar = {
        NavigationBar(
            containerColor = Color(0xFF474444), // Deep Charcoal
            contentColor = Color.White,
            tonalElevation = 8.dp
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                    label = { Text(screen.title, fontSize = 10.sp) },
                    selected = currentRoute == screen.route,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00E5FF), // Electric Blue
                        selectedTextColor = Color(0xFF00E5FF),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color(0xFF1E1E1E) // Subtle highlight circle
                    ),
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
    ) { innerPadding ->
        // This is where your NavHost goes, using innerPadding to avoid overlap
        Box(modifier = Modifier.padding(innerPadding)) {
            // Your existing NavHost logic here
        }
    }
}