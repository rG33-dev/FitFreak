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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun EnduranceLevelScreen() {
    var distanceInMeters by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }
    var showResult by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }


    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Calorie & Macro Tracker")},
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
        if (showInfoDialog) {
            AlertDialog(
                onDismissRequest = { showInfoDialog = false },
                title = { Text(text = "About the Cooper Test") },
                text = {
                    Column {
                        Text("The Cooper Test is a physical fitness test designed by Kenneth H. Cooper in 1968.")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("• Objective: Run as far as possible in 12 minutes.")
                        Text("• Measures: Aerobic fitness and VO2 Max estimation.")
                        Text("• Standard: 2800m+ is considered 'Elite' for most age groups.")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showInfoDialog = false }) {
                        Text("Got it")
                    }
                }
            )
        }
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
                "How many meters can you run in 12 minutes?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            // Gender Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterChip(
                    selected = isMale,
                    onClick = { isMale = true },
                    label = { Text("Male") }
                )
                FilterChip(
                    selected = !isMale,
                    onClick = { isMale = false },
                    label = { Text("Female") }
                )
            }

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Your Age") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = distanceInMeters,
                onValueChange = { distanceInMeters = it },
                label = { Text("Distance (Meters)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = { if (distanceInMeters.isNotEmpty()) showResult = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Check My Level")
            }

            if (showResult) {
                val dist = distanceInMeters.toDoubleOrNull() ?: 0.0
                val userAge = age.toIntOrNull() ?: 25
                val enduranceData = calculateEndurance(dist, userAge, isMale)

                EnduranceResultCard(enduranceData)
            }
        }
    }
}

@Composable
fun EnduranceResultCard(data: EnduranceResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = data.color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Endurance Level", style = MaterialTheme.typography.titleMedium)
            Text(
                text = data.level,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = data.color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Example of a VO2 Max Estimation
            Text(
                text = "Estimated VO2 Max: ${data.vo2Max}",
                modifier = Modifier.padding(top = 12.dp),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

data class EnduranceResult(
    val level: String,
    val description: String,
    val color: Color,
    val vo2Max: String
)

private fun calculateEndurance(meters: Double, age: Int, isMale: Boolean): EnduranceResult {
    // Formula for VO2 Max based on Cooper Test
    val vo2MaxVal = (meters - 504.9) / 44.73
    val formattedVO2 = "%.1f ml/kg/min".format(vo2MaxVal)

    return when {
        meters >= 2800 -> EnduranceResult(
            "Elite",
            "You are in the top 5% of athletes.",
            Color(0xFF4CAF50),
            formattedVO2
        )

        meters >= 2400 -> EnduranceResult(
            "Good",
            "Great cardiovascular health.",
            Color(0xFF8BC34A),
            formattedVO2
        )

        meters >= 2000 -> EnduranceResult(
            "Average",
            "Moderate level. Keep training!",
            Color(0xFFFFC107),
            formattedVO2
        )

        meters >= 1600 -> EnduranceResult(
            "Fair",
            "Consider increasing cardio sessions.",
            Color(0xFFFF9800),
            formattedVO2
        )

        else -> EnduranceResult(
            "Poor",
            "Focus on building your base stamina.",
            Color(0xFFF44336),
            formattedVO2
        )
    }
}