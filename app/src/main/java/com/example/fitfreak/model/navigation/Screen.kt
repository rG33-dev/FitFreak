package com.example.fitfreak.model.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("main_screen", "Home", Icons.Default.Home)
    object Tools : Screen("tools_screen", "Tools", Icons.Default.Build)
    object Articles : Screen("articles_screen", "Articles", Icons.Default.Article)
}