package com.example.fitfreak.presentation.MainScreens

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
import com.example.fitfreak.AddOns.components.AssessmentInputField
import com.example.fitfreak.model.viewModel.FitnessViewModel2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentScreen(navController: NavHostController, viewModel: FitnessViewModel2) {
    var pushups by remember { mutableStateOf("0") }
    var plankSeconds by remember { mutableStateOf("0") }
    var squatHoldSeconds by remember { mutableStateOf("0") }
    var runMinutes by remember { mutableStateOf("0") }

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


                    val score =
                        ((p * 1.5) + (pl / 3) + (s / 4) + (40 - (r * 3))).toInt().coerceIn(10, 99)


                    viewModel.history
                    viewModel.saveResult(score,p,pl,r,s)


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = electricCyan),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("CALCULATE MY SCORE", color = Color.Black, fontWeight = FontWeight.ExtraBold)
            }


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

