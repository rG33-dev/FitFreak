package com.example.fitfreak.calculators

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SexualHealthScreen() {
    var isMale by remember { mutableStateOf(true) }
    var exerciseFrequency by remember { mutableStateOf(2f) } // Default: 1-3 times/week
    var stressLevel by remember { mutableStateOf(2f) } // Default: Moderate
    var sleepHours by remember { mutableStateOf(1f) } // Default: 5-6 hours
    var alcoholIntake by remember { mutableStateOf(1f) } // Default: Socially
    var showResult by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    "Sexual Wellness Score",
                    fontWeight = FontWeight.Bold
                )
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Gender Selection
            GenderSelection(isMale = isMale, onGenderSelect = { isMale = it })

            // Questionnaire Items
            QuestionnaireItem(
                "1. How often do you exercise per week?",
                listOf("Sedentary", "1-3 times", "4-5 times", "Daily")
            ) { exerciseFrequency = it }
            QuestionnaireItem(
                "2. How would you describe your daily stress level?",
                listOf("Low", "Moderate", "High", "Very High")
            ) { stressLevel = it }
            QuestionnaireItem(
                "3. How many hours of quality sleep do you get per night?",
                listOf("< 5", "5-6", "7-8", "> 8")
            ) { sleepHours = it }
            Questionnaireİtem(
                "4. What's your alcohol consumption like?",
                listOf("None", "Socially", "Regularly", "Heavily")
            ) {}//{ alcoholIntake = it }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showResult = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Calculate My Wellness Score")
            }

            if (showResult) {
                val score = calculateSexualHealthScore(
                    exerciseFrequency,
                    stressLevel,
                    sleepHours,
                    alcoholIntake
                )
                SexualHealthResultCard(score)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun Questionnaireİtem(x0: String, x1: List<String>, content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun GenderSelection(isMale: Boolean, onGenderSelect: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "I am:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 16.dp)
        )
        FilterChip(selected = isMale, onClick = { onGenderSelect(true) }, label = { Text("Male") })
        Spacer(modifier = Modifier.width(16.dp))
        FilterChip(
            selected = !isMale,
            onClick = { onGenderSelect(false) },
            label = { Text("Female") })
    }
}

@Composable
fun QuestionnaireItem(question: String, options: List<String>, onValueChange: (Float) -> Unit) {
    var sliderPosition by remember { mutableStateOf(1f) }
    Column {
        Text(question, style = MaterialTheme.typography.titleSmall)
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onValueChange(it)
            },
            valueRange = 0f..(options.size - 1).toFloat(),
            steps = options.size - 2
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            options.forEach { Text(it, style = MaterialTheme.typography.labelSmall) }
        }
    }
}

@Composable
fun SexualHealthResultCard(score: Int) {
    val (level, color, advice) = when {
        score >= 85 -> Triple(
            "Optimal",
            Color(0xFF4CAF50),
            "Your lifestyle choices are positively impacting your vitality. Keep it up!"
        )

        score >= 65 -> Triple(
            "Good",
            Color(0xFF8BC34A),
            "You have a solid foundation. Small tweaks in stress or sleep could boost your well-being."
        )

        score >= 45 -> Triple(
            "Moderate",
            Color(0xFFFFC107),
            "Your health is likely being impacted by stress or lifestyle habits. Focus on improving sleep and exercise."
        )

        else -> Triple(
            "Needs Attention",
            Color(0xFFF44336),
            "Prioritizing stress management, regular exercise, and better sleep is highly recommended."
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                level,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                "Wellness Score: $score/100",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text("Recommendation", style = MaterialTheme.typography.titleMedium)
            Text(
                text = advice,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun calculateSexualHealthScore(
    exercise: Float,
    stress: Float,
    sleep: Float,
    alcohol: Float
): Int {
    // Scoring logic: Higher points for better habits.
    // Max score is 100. Each factor contributes up to 25 points.

    // Exercise (0=Sedentary, 1=Light, 2=Moderate, 3=Daily)
    val exerciseScore = when (exercise.toInt()) {
        0 -> 5
        1 -> 15
        2 -> 20
        3 -> 25
        else -> 0
    }

    // Stress (0=Low, 1=Moderate, 2=High, 3=Very High) - REVERSED
    val stressScore = when (stress.toInt()) {
        0 -> 25
        1 -> 20
        2 -> 10
        3 -> 5
        else -> 0
    }

    // Sleep (0=<5, 1=5-6, 2=7-8, 3=>8)
    val sleepScore = when (sleep.toInt()) {
        0 -> 5
        1 -> 15
        2 -> 25
        3 -> 20 // Diminishing returns after 8 hours for some
        else -> 0
    }

    // Alcohol (0=None, 1=Socially, 2=Regularly, 3=Heavily) - REVERSED
    val alcoholScore = when (alcohol.toInt()) {
        0 -> 25
        1 -> 20
        2 -> 10
        3 -> 0
        else -> 0
    }

    return exerciseScore + stressScore + sleepScore + alcoholScore
}