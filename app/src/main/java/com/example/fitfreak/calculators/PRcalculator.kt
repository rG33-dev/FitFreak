
package com.example.fitfreak.calculators

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlin.run


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PRCalculatorScreen() {
    var weightText by remember { mutableStateOf("") }
    var repsText by remember { mutableStateOf("") }
    var selectedExercise by remember { mutableStateOf("Bench Press") }
    var showResult by remember { mutableStateOf(false) }

    val exercises = listOf("Bench Press", "Deadlift", "Squat", "Overhead Press")
    val scrollState = rememberScrollState()

    // Lottie Animation Logic
   // val composition by rememberLottieComposition(LottieCompositionSpec.run { RawRes(R.raw.workout_anim) })
    // Note: Ensure you have a JSON lottie file in res/raw/workout_anim.json

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PR / 1-Rep Max Calculator", fontWeight = FontWeight.Bold) }
            )
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
            // Exercise Selector
            Text("Select Compound Lift", style = MaterialTheme.typography.titleMedium)
            ScrollableTabRow(
                selectedTabIndex = exercises.indexOf(selectedExercise),
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                divider = {}
            ) {
                exercises.forEach { exercise ->
                    Tab(
                        selected = selectedExercise == exercise,
                        onClick = { selectedExercise = exercise },
                        text = { Text(exercise) }
                    )
                }
            }

            // Inputs
            OutlinedTextField(
                value = weightText,
                onValueChange = { weightText = it },
                label = { Text("Weight Lifted (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Text("🏋️", modifier = Modifier.padding(start = 8.dp)) }
            )

            OutlinedTextField(
                value = repsText,
                onValueChange = { repsText = it },
                label = { Text("Reps Performed") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                supportingText = { Text("Best results with 1-10 reps") }
            )

            Button(
                onClick = {
                    if (weightText.isNotEmpty() && repsText.isNotEmpty()) showResult = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Calculate Estimated PR", fontSize = 16.sp)
            }

            if (showResult) {
                val weight = weightText.toDoubleOrNull() ?: 0.0
                val reps = repsText.toDoubleOrNull() ?: 0.0

                // Brzycki Formula: Weight / ( 1.0278 - ( 0.0278 * Reps ) )
                val oneRepMax = weight / (1.0278 - (0.0278 * reps))

                ResultDisplay(oneRepMax, selectedExercise)
            }
        }
    }
}

@Composable
fun ResultDisplay(oneRepMax: Double, exercise: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Estimated 1RM for $exercise", style = MaterialTheme.typography.labelLarge)
            Text(
                text = "${oneRepMax.toInt()} KG",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Percentage Breakdown Table
            Text("Strength Percentages", fontWeight = FontWeight.Bold)
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            PercentageRow("95% (Power)", oneRepMax * 0.95)
            PercentageRow("85% (Strength)", oneRepMax * 0.85)
            PercentageRow("75% (Hypertrophy)", oneRepMax * 0.75)
            PercentageRow("65% (Endurance)", oneRepMax * 0.65)
        }
    }
}

@Composable
fun PercentageRow(label: String, value: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text("${value.toInt()} kg", fontWeight = FontWeight.Bold)
    }
}