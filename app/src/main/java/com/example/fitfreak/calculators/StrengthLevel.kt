package com.example.fitfreak.calculators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.HorizontalDivider
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StrengthLevelScreen() {
    var bodyWeight by remember { mutableStateOf("") }
    var benchWeight by remember { mutableStateOf("") }
    var squatWeight by remember { mutableStateOf("") }
    var deadliftWeight by remember { mutableStateOf("") }
    var showResults by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Strength Level (Nippard Scale)") })
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
                "Find your rank from Rookie to Advanced based on your 1-Rep Maxes.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            OutlinedTextField(
                value = bodyWeight,
                onValueChange = { bodyWeight = it },
                label = { Text("Your Body Weight (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            LiftInput("Bench Press", benchWeight) { benchWeight = it }
            LiftInput("Squat", squatWeight) { squatWeight = it }
            LiftInput("Deadlift", deadliftWeight) { deadliftWeight = it }

            Button(
                onClick = { if (bodyWeight.isNotEmpty()) showResults = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Calculate My Level")
            }

            if (showResults) {
                val bw = bodyWeight.toDoubleOrNull() ?: 1.0
                StrengthResultSection(
                    bw,
                    benchWeight.toDoubleOrNull() ?: 0.0,
                    squatWeight.toDoubleOrNull() ?: 0.0,
                    deadliftWeight.toDoubleOrNull() ?: 0.0
                )
            }
        }
    }
}

@Composable
fun LiftInput(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("$label Max (kg)") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun StrengthResultSection(bw: Double, bench: Double, squat: Double, deadlift: Double) {
    val benchLevel = getLevel(bench / bw, "bench")
    val squatLevel = getLevel(squat / bw, "squat")
    val deadLevel = getLevel(deadlift / bw, "deadlift")

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LevelCard("Bench Press", benchLevel)
        LevelCard("Squat", squatLevel)
        LevelCard("Deadlift", deadLevel)
    }
}

@Composable
fun LevelCard(lift: String, result: StrengthResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = result.color.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(lift, style = MaterialTheme.typography.labelLarge)
                Text(
                    result.level,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = result.color
                )
            }
            Text(result.desc, style = MaterialTheme.typography.bodySmall)
        }
    }
}

data class StrengthResult(val level: String, val color: Color, val desc: String)

private fun getLevel(ratio: Double, lift: String): StrengthResult {
    // Ratios based on Jeff Nippard's "How Strong Should You Be" standards
    val (rookie, newbie, beginner, intermediate) = when (lift) {
        "bench" -> listOf(0.5, 0.8, 1.0, 1.5)
        "squat" -> listOf(0.6, 1.0, 1.25, 2.0)
        else -> listOf(0.8, 1.2, 1.5, 2.5) // Deadlift
    }

    return when {
        ratio >= intermediate -> StrengthResult(
            "Advanced",
            Color(0xFFD32F2F),
            "Elite level strength."
        )

        ratio >= beginner -> StrengthResult("Intermediate", Color(0xFFF57C00), "Solid foundation.")
        ratio >= newbie -> StrengthResult("Beginner", Color(0xFF388E3C), "Showing progress.")
        ratio >= rookie -> StrengthResult("Newbie", Color(0xFF1976D2), "Just getting started.")
        else -> StrengthResult("Rookie", Color(0xFF757575), "Learning the form.")
    }
}