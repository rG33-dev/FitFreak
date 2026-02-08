package com.example.fitfreak.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun PreviewScreen(navController: NavHostController) {

    // List of Random Quotes
    val quotes = listOf(
        "The only bad workout is the one that didn't happen.",
        "Don't stop when you're tired. Stop when you're done.",
        "Discipline is doing what needs to be done, even if you don't want to do it.",
        "Your body can stand almost anything. It’s your mind that you have to convince.",
        "Focus on progress, not perfection."
    )

    val randomQuote = quotes.random()

    // Navigation Logic: Wait 1 second then move to Main Screen
    LaunchedEffect(Unit) {
        delay(1000) // 1 second delay
        navController.navigate("main_screen") {
            popUpTo("preview_screen") { inclusive = true } // Remove preview from backstack
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Black Background
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {

            // --- PLACEHOLDER FOR IMAGE ---
            // Replace R.drawable.your_logo with your actual resource
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                 Image(
                     painter = painterResource(id = com.example.fitfreak.R.drawable.logo),
                     contentDescription = "App Logo",
                     modifier = Modifier.fillMaxSize(),
                     contentScale = ContentScale.Fit
                 )
              //   Text("LOGO HERE", color = Color.Gray, fontSize = 12.sp)
            }

            // --- QUOTE ---
            Text(
                text = "\"$randomQuote\"",
                color = Color.White,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "FitFreak",
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 4.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}