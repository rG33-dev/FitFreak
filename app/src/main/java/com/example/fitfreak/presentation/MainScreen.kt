package com.example.fitfreak.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.example.fitfreak.R
import com.example.fitfreak.components.SectionCard
import com.example.fitfreak.data.AuthViewModel

// Modern Palette
val DeepBlack = Color(0xFF000000)
val SurfaceGray = Color(0xFF121212)
val ElectricCyan = Color(0xFF00E5FF)

@Composable
fun MainScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.htbt)) // Ensure kick.json is in res/raw

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlack)
            .verticalScroll(scrollState),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "FIT FREAK",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp,
                    letterSpacing = 2.sp
                )
                Text(
                    "BEYOND AVERAGE",
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = ElectricCyan)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Central Animation & Score Display
        Box(
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(140.dp))
                .background(
                    Brush.radialGradient(
                        listOf(
                            ElectricCyan.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(200.dp)
            )

            // Floating Score Preview (Mock data)
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp),
                color = SurfaceGray,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.5f))
            ) {
                Text(
                    "FIT SCORE: --",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // The Fitness Assessment Card
        SectionCard(
            title = "Performance Audit",
            subtitle = "Get your FitFreak Score",
            icon = Icons.Default.TrendingUp,
            accentColor = Color(0xFF00E5FF),
            onClick = { navController.navigate("assessment_screen") }
        )


        Spacer(modifier = Modifier.height(24.dp))

        // Quick Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatSmallCard("Strength", "Level: ?", Modifier.weight(1f).padding(start = 12.dp,end = 12.dp)



                )
            StatSmallCard("Endurance", "VO2: ?", Modifier.weight(1f).padding(start = 12.dp,end = 12.dp))
        }

        Spacer(modifier = Modifier.height(30.dp)) // Padding for bottom nav
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatSmallCard("Button 3", "Level: ?", Modifier.weight(1f).padding(start = 12.dp,end = 12.dp))
            StatSmallCard("Button 4", "VO2: ?", Modifier.weight(1f).padding(start = 12.dp,end = 12.dp))
        }
        Spacer(modifier = Modifier.height(80.dp)) // Padding for bottom nav
        Button(
            onClick = { navController.navigate("progress_screen") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, Color.DarkGray)
        ) {
            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = Color(0xFF00E5FF))
            Spacer(Modifier.width(8.dp).padding(start = 20.dp,end = 20.dp))
            Text("VIEW EVOLUTION PROGRESS", color = Color.White)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessAssessmentCard(onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(0.5.dp, Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Icon(
                Icons.Default.FitnessCenter,
                contentDescription = null,
                tint = ElectricCyan,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "PERFORMANCE AUDIT",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
            Text(
                "Take a 2-minute test to calculate your global fitness rank and biological age.",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricCyan),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("START ASSESSMENT", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun StatSmallCard(label: String, value: String, modifier: Modifier) {
    Surface(
        modifier = modifier,
        color = SurfaceGray,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(0.5.dp, Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, color = Color.Gray, fontSize = 12.sp)
            Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}