import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import com.example.fitfreak.R
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.fitfreak.model.viewModel.AuthState
import com.example.fitfreak.model.viewModel.AuthViewModel
import com.example.fitfreak.presentation.UserScreens.ElectricCyan
import com.example.fitfreak.presentation.UserScreens.FitTextField

@Composable
fun SignUpScreen(onNavigateToLogin: () -> Unit, authViewModel: AuthViewModel,onSignUpSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.signflow))
    val signUpState by authViewModel.authState


    LaunchedEffect(signUpState) {
        if (signUpState is AuthState.Authenticated) {
            onSignUpSuccess()
        }





    }








    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlack),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LottieAnimation(
                composition = lottieComposition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "START EVOLUTION",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )

            FitTextField(
                value = name,
                onValueChange = { name = it },
                label = "NAME",
                icon = Icons.Default.Person
            )

            FitTextField(
                value = email,
                onValueChange = { email = it },
                label = "EMAIL",
                icon = Icons.Default.Mail
            )

            FitTextField(
                value = password,
                onValueChange = { password = it },
                label = "PASSWORD",
                icon = Icons.Default.Lock,
                isPassword = true
            )

            Button(
                onClick = { authViewModel.signUp(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)

            ) {
                Text(
                    "JOIN THE FREAKS",
                    color = Color.Black,
                    fontWeight = FontWeight.Black
                )
            }

            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("ALREADY A FREAK? ", color = Color.Gray)
                Text("LOG IN", color = ElectricCyan, fontWeight = FontWeight.Bold)
            }




        }
    }
}
