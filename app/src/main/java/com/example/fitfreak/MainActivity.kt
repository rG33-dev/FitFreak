package com.example.fitfreak


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fitfreak.model.navigation.FitFreakApp
import com.example.fitfreak.ui.theme.FitFreakTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitFreakTheme {

                FitFreakApp()


            }
        }
    }
}
