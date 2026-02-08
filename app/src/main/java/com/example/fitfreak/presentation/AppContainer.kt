package com.example.fitfreak.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FitFreakContainer(content: @Composable () -> Unit) {
    // This Surface ensures every screen has a dark background by default
    Surface(
        modifier = Modifier.fillMaxSize(),color = Color.Black // Or MaterialTheme.colorScheme.background
    ) {
        content()
    }
}