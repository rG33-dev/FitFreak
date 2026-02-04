package com.example.fitfreak.calculators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessLevelScreen() {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var pushups by remember { mutableStateOf("") }
    var runDistance by remember { mutableStateOf("") } // 12-min run in meters
    var showResult by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    "Overall Fitness Score",
                    fontWeight = FontWeight.Bold
                )
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Enter your stats to calculate your overall fitness rank.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )

            // Input Fields
            FitnessInput(
                value = weight,
                onValueChange = { weight = it },
                label = "Weight (kg)",
                icon = "⚖️"
            )
            FitnessInput(
                value = height,
                onValueChange = { height = it },
                label = "Height (cm)",
                icon = "📏"
            )
            FitnessInput(
                value = pushups,
                onValueChange = { pushups = it },
                label = "Max Pushups (in 1 min)",
                icon = "💪"
            )
            FitnessInput(
                value = runDistance,
                onValueChange = { runDistance = it },
                label = "12-Min Run Distance (m)",
                icon = "🏃"
            )

            Button(
                onClick = { if (weight.isNotBlank() && height.isNotBlank()) showResult = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Calculate Fitness Rank")
            }

            if (showResult) {
                val score = calculateFitnessScore(
                    weight.toDoubleOrNull() ?: 0.0,
                    height.toDoubleOrNull() ?: 0.0,
                    pushups.toIntOrNull() ?: 0,
                    runDistance.toDoubleOrNull() ?: 0.0
                )
                FitnessResultDisplay(score)
            }
        }
    }
}

@Composable
fun FitnessInput(value: String, onValueChange: (String) -> Unit, label: String, icon: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Text(icon, modifier = Modifier.padding(start = 8.dp)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}

@Composable
fun FitnessResultDisplay(score: Int) {
    val (rank, color, desc) = when {
        score >= 90 -> Triple("Elite Athlete", Color(0xFFD4AF37), "Top 5% of the population.")
        score >= 75 -> Triple("Advanced", Color(0xFF4CAF50), "Excellent strength and stamina.")
        score >= 50 -> Triple("Intermediate", Color(0xFF2196F3), "You have a solid fitness base.")
        score >= 30 -> Triple("Novice", Color(0xFFFF9800), "Room for improvement. Keep going!")
        else -> Triple("Beginner", Color(0xFFF44336), "Start slow and build consistency.")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Your Fitness Level", style = MaterialTheme.typography.titleMedium)
            Text(
                rank,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { score / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = color,
                trackColor = color.copy(alpha = 0.2f)
            )

            Text(
                "Score: $score/100",
                modifier = Modifier.padding(top = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                desc,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun calculateFitnessScore(w: Double, h: Double, p: Int, d: Double): Int {
    if (w <= 0 || h <= 0) return 0

    // 1. BMI Component (30 points)
    val bmi = w / (h / 100).pow(2.0)
    val bmiScore = when {
        bmi in 18.5..24.9 -> 30
        bmi in 25.0..29.9 -> 20
        else -> 10
    }

    // 2. Strength Component (Pushups) (35 points)
    val pushupScore = (p * 1.5).coerceAtMost(35.0).toInt()

    // 3. Endurance Component (Cooper Test) (35 points)
    // Average person 12min run is ~2000m. 3000m is elite.
    val enduranceScore = ((d / 3000) * 35).coerceAtMost(35.0).toInt()

    return (bmiScore + pushupScore + enduranceScore).coerceIn(0, 100)
}