package com.example.fitfreak.calculators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxMuscleCalculatorScreen() {
    var height by remember { mutableStateOf("") }
    var wristGirth by remember { mutableStateOf("") }
    var ankleGirth by remember { mutableStateOf("") }
    var bodyFatGoal by remember { mutableStateOf("10") } // Default to 10% shredded
    var showResult by remember { mutableStateOf(false) }

    var showInfoDialog by remember { mutableStateOf(false) }


    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Max muscle gain ")},
                actions = {
                    // INFO BUTTON
                    IconButton(onClick = { showInfoDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }



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


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                // Paste your LottieAnimation here
            }
            Text(
                "Estimate your maximum natural muscle mass based on your skeletal frame.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )

            // Input Fields
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height (cm)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = wristGirth,
                onValueChange = { wristGirth = it },
                label = { Text("Wrist Girth (cm)") },
                placeholder = { Text("Measure at thinnest point") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = ankleGirth,
                onValueChange = { ankleGirth = it },
                label = { Text("Ankle Girth (cm)") },
                placeholder = { Text("Measure at thinnest point") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Text("Target Body Fat %: $bodyFatGoal%", style = MaterialTheme.typography.labelLarge)
            Slider(
                value = bodyFatGoal.toFloat(),
                onValueChange = { bodyFatGoal = it.toInt().toString() },
                valueRange = 5f..20f,
                steps = 15
            )

            Button(
                onClick = { if (height.isNotBlank() && wristGirth.isNotBlank()) showResult = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Calculate Potential")
            }

            if (showResult) {
                val potential = calculateMaxMuscle(
                    height.toDoubleOrNull() ?: 0.0,
                    wristGirth.toDoubleOrNull() ?: 0.0,
                    ankleGirth.toDoubleOrNull() ?: 0.0,
                    bodyFatGoal.toDoubleOrNull() ?: 10.0
                )
                MaxMuscleResultCard(potential)
            }
        }
    }
}

@Composable
fun MaxMuscleResultCard(result: MaxMuscleResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Maximum Natural Weight", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${"%.1f".format(result.maxWeight)} kg",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                "at ${result.bodyFat}% Body Fat",
                style = MaterialTheme.typography.bodyLarge
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp) )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Lean Muscle Mass:")
                Text("${"%.1f".format(result.leanMass)} kg", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Note: This is an estimate of your 'Genetic Ceiling' after 5-10 years of perfect training and nutrition.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

data class MaxMuscleResult(
    val maxWeight: Double,
    val leanMass: Double,
    val bodyFat: Double
)

private fun calculateMaxMuscle(h: Double, w: Double, a: Double, bf: Double): MaxMuscleResult {
    // Casey Butt's Formula (simplified for centimeters)
    // Max Lean Body Mass = H^1.5 * [(sqrt(W)/22.66) + (sqrt(A)/60.1)] * (BF/224 + 1)

    val heightInInches = h / 2.54
    val wristInInches = w / 2.54
    val ankleInInches = a / 2.54

    val leanMassLbs = heightInInches.pow(1.5) *
            ((Math.sqrt(wristInInches) / 22.66) + (Math.sqrt(ankleInInches) / 60.1)) *
            (bf / 224 + 1)

    val leanMassKg = leanMassLbs * 0.453592
    val totalWeightKg = leanMassKg / (1 - (bf / 100))

    return MaxMuscleResult(totalWeightKg, leanMassKg, bf)
}