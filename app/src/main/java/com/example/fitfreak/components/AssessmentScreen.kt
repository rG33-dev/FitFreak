package com.example.fitfreak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentScreen(navController: NavHostController) {
    var pushups by remember { mutableStateOf("") }
    var plankSeconds by remember { mutableStateOf("") }
    var squatHoldSeconds by remember { mutableStateOf("") }
    var runMinutes by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val electricCyan = Color(0xFF00E5FF)

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "FITNESS AUDIT",
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                "Enter your maximum performance metrics to calculate your global rank.",
                color = Color.Gray,
                fontSize = 14.sp
            )

            AssessmentInputField("Max Pushups (to failure)", pushups) { pushups = it }
            AssessmentInputField("Plank Hold (seconds)", plankSeconds) { plankSeconds = it }
            AssessmentInputField(
                "Bodyweight Squat Hold (sec)",
                squatHoldSeconds
            ) { squatHoldSeconds = it }
            AssessmentInputField("1km Run Time (minutes)", runMinutes) { runMinutes = it }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val p = pushups.toIntOrNull() ?: 0
                    val pl = plankSeconds.toIntOrNull() ?: 0
                    val s = squatHoldSeconds.toIntOrNull() ?: 0
                    val r = runMinutes.toFloatOrNull() ?: 10f

                    // FitFreak Algorithm: Higher is better (Max ~100)
                    val score =
                        ((p * 1.5) + (pl / 3) + (s / 4) + (40 - (r * 3))).toInt().coerceIn(10, 99)

                    // Navigate back with result or save to ViewModel
                    navController.previousBackStackEntry?.savedStateHandle?.set("fit_score", score)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = electricCyan),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("CALCULATE MY SCORE", color = Color.Black, fontWeight = FontWeight.ExtraBold)
            }

            // Helpful tip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A1A1A), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = electricCyan)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Be honest. Only true data leads to growth.",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun AssessmentInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(
            label,
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF121212),
                unfocusedContainerColor = Color(0xFF121212),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color(0xFF00E5FF),
                unfocusedIndicatorColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
    }
}