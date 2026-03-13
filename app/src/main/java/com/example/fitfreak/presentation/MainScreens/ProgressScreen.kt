package com.example.fitfreak.presentation.MainScreens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


import com.example.fitfreak.model.viewModel.FitnessViewModel2

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(navController: NavHostController) {
    // In a real app, these would come from a Database/ViewModel


    val electricCyan = Color(0xFF00E5FF)
    val deepBlack = Color(0xFF000000)
    val surfaceGray = Color(0xFF121212)

    val history by FitnessViewModel2(LocalContext.current.applicationContext as Application).history.collectAsState(initial = emptyList())


    // Calculate stats based on real data
    val currentScore = history.firstOrNull()?.score?.toFloat() ?: 0f
    val previousScore = if (history.size > 1) {
        history[1].score.toFloat()
    } else currentScore
    val improvement = if (previousScore > 0) ((currentScore - previousScore) / previousScore * 100).toInt() else 0

    Scaffold(
        containerColor = deepBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "PERFORMANCE PROGRESS",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = deepBlack)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    "Your evolution from Human to Freak.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            // --- CENTRAL PROGRESS CHART (Visual Representation) ---
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(surfaceGray)
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressSection(currentScore, electricCyan)
                }
            }

            // --- COMPARISON STATS ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatComparisonCard(
                        label = "Improvement",
                        value = "+${(currentScore - previousScore).toInt()}%",
                        subtext = "Since last audit",
                        modifier = Modifier.weight(1f),
                        accentColor = electricCyan
                    )
                    StatComparisonCard(
                        label = "Global Rank",
                        value = "Top 12%",
                        subtext = "Vs all users",
                        modifier = Modifier.weight(1f),
                        accentColor = Color.White
                    )
                }
            }

            // --- ATTRIBUTE BREAKDOWN ---
            item {
                Text(
                    "ATTRIBUTE MASTERY",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                AttributeBar("Strength", 0.85f, electricCyan)
                AttributeBar("Endurance", 0.60f, electricCyan)
                AttributeBar("Recovery", 0.45f, Color.Gray)
                AttributeBar("Power", 0.72f, electricCyan)
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
fun CircularProgressSection(score: Float, color: Color) {
    val animatedProgress by animateFloatAsState(
        targetValue = score / 100f,
        animationSpec = tween(durationMillis = 1500)
    )

    Box(contentAlignment = Alignment.Center) {
        // Background Circle
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.size(180.dp),
            color = Color.DarkGray.copy(alpha = 0.3f),
            strokeWidth = 12.dp,
        )
        // Actual Progress
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.size(180.dp),
            color = color,
            strokeWidth = 12.dp,
            strokeCap = StrokeCap.Round
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${score.toInt()}",
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "FIT SCORE",
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}

@Composable
fun StatComparisonCard(
    label: String,
    value: String,
    subtext: String,
    modifier: Modifier,
    accentColor: Color
) {
    Surface(
        modifier = modifier,
        color = Color(0xFF1A1A1A),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, color = Color.Gray, fontSize = 12.sp)
            Text(value, color = accentColor, fontSize = 22.sp, fontWeight = FontWeight.Black)
            Text(subtext, color = Color.DarkGray, fontSize = 10.sp)
        }
    }
}

@Composable
fun AttributeBar(label: String, progress: Float, color: Color) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Color.White, fontSize = 13.sp)
            Text(
                "${(progress * 100).toInt()}%",
                color = color,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = color,
            trackColor = Color.DarkGray.copy(alpha = 0.2f)
        )
    }
}