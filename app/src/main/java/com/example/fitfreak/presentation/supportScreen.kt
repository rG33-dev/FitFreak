package com.example.fitfreak.presentation

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitfreak.data.sendEmail
import com.google.firebase.auth.FirebaseAuth


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SupportScreen(navController: NavHostController) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val electricCyan = Color(0xFF00E5FF)
        val context = LocalContext.current

        Scaffold(
            containerColor = Color.Black,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "SETTINGS & SUPPORT",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Black)
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // --- ACCOUNT SECTION ---
                item { SupportHeader("ACCOUNT") }
                item {
                    SupportItem(
                        icon = Icons.Default.Person,
                        title = "Profile Details",
                        subtitle = user?.email ?: "Not Logged In",
                        onClick = { /* Navigate to Profile */ }
                    )
                }
                item {
                    SupportItem(
                        icon = Icons.Default.Logout,
                        title = "Sign Out",
                        subtitle = "Log out of FitFreak",
                        iconColor = Color.Red,
                        onClick = {
                            auth.signOut()
                            navController.navigate("login_screen") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // --- PAYMENTS & BILLING ---
                item { SupportHeader("BILLING") }
                item {
                    SupportItem(
                        icon = Icons.Default.Payments,
                        title = "Payment Methods",
                        subtitle = "Manage Razorpay / UPI",
                        onClick = { /* Razorpay Logic */ }
                    )
                }
                item {
                    SupportItem(
                        icon = Icons.Default.ReceiptLong,
                        title = "Transaction History",
                        subtitle = "View your invoices",
                        onClick = { }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // --- HELP & DEV ---
                item { SupportHeader("HELP & FEEDBACK") }
                item {
                    SupportItem(
                        icon = Icons.Default.Email,
                        title = "Contact Developer",
                        subtitle = "Report bugs or request features",
                        onClick = { sendEmail(context)}
                    )
                }
                item {
                    SupportItem(
                        icon = Icons.Default.Star,
                        title = "Rate FitFreak",
                        subtitle = "Support us on Play Store",
                        onClick = { }
                    )
                }

                // --- SYSTEM INFO ---
                item { Spacer(modifier = Modifier.height(32.dp)) }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "VERSION 1.0.4-ELITE",
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "Made for Freaks by Freaks",
                            color = Color.DarkGray.copy(alpha = 0.5f),
                            fontSize = 10.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(40.dp)) }
            }
        }
    }

    @Composable
    fun SupportHeader(text: String) {
        Text(
            text = text,
            color = Color(0xFF00E5FF),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
        )
    }

    @Composable
    fun SupportItem(
        icon: ImageVector,
        title: String,
        subtitle: String,
        iconColor: Color = Color.White,
        onClick: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            color = Color(0xFF121212),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        title,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(subtitle, color = Color.Gray, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.DarkGray)
            }
        }
    }
