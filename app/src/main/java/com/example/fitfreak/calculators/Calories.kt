import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriesCalculatorScreen() {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }
    var activityLevel by remember { mutableDoubleStateOf(1.2) } // Sedentary default
    var showResults by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Calorie & Macro Tracker") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Gender Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterChip(selected = isMale, onClick = { isMale = true }, label = { Text("Male") })
                FilterChip(
                    selected = !isMale,
                    onClick = { isMale = false },
                    label = { Text("Female") })
            }

            OutlinedTextField(
                value = weight, onValueChange = { weight = it },
                label = { Text("Weight (kg)") }, modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = height, onValueChange = { height = it },
                label = { Text("Height (cm)") }, modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = age, onValueChange = { age = it },
                label = { Text("Age") }, modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Text("Activity Level", style = MaterialTheme.typography.titleSmall)
            ActivitySlider(activityLevel) { activityLevel = it }

            Button(
                onClick = { showResults = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = weight.isNotEmpty() && height.isNotEmpty() && age.isNotEmpty()
            ) {
                Text("Calculate Requirements")
            }

            if (showResults) {
                val bmr = calculateBMR(
                    weight.toDoubleOrNull() ?: 0.0,
                    height.toDoubleOrNull() ?: 0.0,
                    age.toIntOrNull() ?: 0,
                    isMale
                )
                val tdee = bmr * activityLevel

                CalorieResultSection(tdee)
            }
        }
    }
}

@Composable
fun CalorieResultSection(tdee: Double) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ResultCard(
            "Maintenance",
            "Maintain current weight",
            tdee,
            MaterialTheme.colorScheme.primary
        )

        Text("Weight Loss (Cutting)", style = MaterialTheme.typography.headlineSmall)
        ResultCard("Mild Cut (0.25kg/week)", "-10% Calories", tdee * 0.9, Color(0xFF4CAF50))
        ResultCard("Moderate Cut (0.5kg/week)", "-20% Calories", tdee * 0.8, Color(0xFFFF9800))
        ResultCard("Aggressive Cut (1kg/week)", "-25% Calories", tdee * 0.75, Color(0xFFF44336))

        Text("Weight Gain (Bulking)", style = MaterialTheme.typography.headlineSmall)
        ResultCard("Lean Bulk", "+10% Calories", tdee * 1.1, Color(0xFF03A9F4))
        ResultCard("Aggressive Bulk", "+20% Calories", tdee * 1.2, Color(0xFF673AB7))
    }
}

@Composable
fun ResultCard(title: String, subtitle: String, calories: Double, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(title, fontWeight = FontWeight.Bold, color = color)
                Text(subtitle, style = MaterialTheme.typography.bodySmall)
            }
            Text("${calories.toInt()} kcal", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun ActivitySlider(currentValue: Double, onValueChange: (Double) -> Unit) {
    val levels = listOf(1.2, 1.375, 1.55, 1.725, 1.9)
    val labels = listOf("Sedentary", "Light", "Moderate", "Active", "Athlete")

    Column {
        Slider(
            value = levels.indexOf(currentValue).toFloat(),
            onValueChange = { onValueChange(levels[it.toInt()]) },
            valueRange = 0f..4f,
            steps = 3
        )
        Text(
            labels[levels.indexOf(currentValue)],
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

private fun calculateBMR(weight: Double, height: Double, age: Int, isMale: Boolean): Double {
    return if (isMale) {
        (10 * weight) + (6.25 * height) - (5 * age) + 5
    } else {
        (10 * weight) + (6.25 * height) - (5 * age) - 161
    }
}


