import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.fitfreak.R
import com.example.fitfreak.data.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

// Professional Dark Palette
val DeepBlack = Color(0xFF000000)
val CharcoalGray = Color(0xFF121212)
val SurfaceLight = Color(0xFF1E1E1E)
val ElectricBlue = Color(0xFF00E5FF) // Accent for a "tech" feel

data class CalculatorMenu(
    val title: String,
    val description: String,
    val route: String,
    val accentColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val auth = FirebaseAuth.getInstance()

    // Auth Check
    LaunchedEffect(auth.currentUser) {
        if (auth.currentUser == null) {
            navController.navigate("signup_screen") { popUpTo(0) }
        }
    }

    val menuItems = listOf(
        CalculatorMenu("BMI", "Body Mass Index", "bmi", Color.White),
        CalculatorMenu("Calories", "Bulk, Cut, Maintain", "calories", Color.White),
        CalculatorMenu("Endurance", "Cooper Test & VO2 Max", "endurance", Color.White),
        CalculatorMenu("PR", "1-Rep Max Calc", "pr", Color.White),
        CalculatorMenu("Fitness Rank", "Health Assessment", "fitness_level", Color.White),
        CalculatorMenu("Genetic Limit", "Max Muscle Mass", "max_muscle", Color.White),
        CalculatorMenu("Strength", "Nippard Scale", "strength_lvl", Color.White),
        CalculatorMenu("Vitality", "Libido & Score", "sexual_health", Color.White)
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.kick))

    Scaffold(
        containerColor = DeepBlack,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlack),
                title = {
                    Column {
                        Text(
                            "FIT SCORE",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        )
                        Text(
                            " FUCK AVERAGE",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        authViewModel.logout()
                        navController.navigate("signup_screen") { popUpTo(0) }
                    }) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout", tint = Color.Gray)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Animation Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Brush.verticalGradient(listOf(SurfaceLight, DeepBlack))),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(180.dp)
                    )
                }
            }

            item {
                Text(
                    "LAB TOOLS",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            // High-End Grid-like Items
            items(menuItems) { menu ->
                ModernMenuCard(menu) {
                    navController.navigate(menu.route)
                }
            }

            // Pro Action
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { /* Razorpay */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceLight),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "UNLOCK PRO ACCESS",
                        color = ElectricBlue,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernMenuCard(menu: CalculatorMenu, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceLight)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Small decorative dot
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(ElectricBlue)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = menu.title.uppercase(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
                Text(
                    text = menu.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}