package com.example.fitfreak.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.fitfreak.R

import com.example.fitfreak.components.ProgressScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay



@Composable
fun PreviewScreen(navController: NavHostController) {
    // Navigation Logic
    LaunchedEffect(Unit) {
        delay(2500) // Slightly longer to let the user read the quote
        val currentUser = FirebaseAuth.getInstance().currentUser
        val destination = if (currentUser != null) "main_screen" else "signup_screen"

        navController.navigate(destination) {
            popUpTo("PreviewScreen") { inclusive = true }
        }
    }

    val quotes = listOf(
        "THE ONLY BAD WORKOUT IS THE ONE THAT DIDN'T HAPPEN.",
        "DON'T STOP WHEN YOU'RE TIRED. STOP WHEN YOU'RE DONE.",
        "DISCIPLINE IS DOING WHAT NEEDS TO BE DONE.",
        "FOCUS ON PROGRESS, NOT PERFECTION."
    )
    val randomQuote = remember { quotes.random() }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.watch))

    // Modern Background with a subtle radial spotlight
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A1A1A), // Dark Gray center
                        Color(0xFF000000)  // Pure Black edges
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            // --- LOGO SECTION ---
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(200.dp)
                )

            }


            Text(
                text = randomQuote,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // --- BRANDING ---
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "FIT SCORE",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 6.sp
                )

                // Cyan/Primary accent line
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(40.dp)
                        .height(2.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ELITE PERFORMANCE",
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
        ) {
            ProgressScreen()
        }
    }
}
