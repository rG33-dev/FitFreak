package com.example.fitfreak.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Data Model for Growth Resources
data class ImprovementResource(
    val title: String,
    val description: String,
    val url: String,
    val type: ResourceType,
    val category: String
)

enum class ResourceType { VIDEO, ARTICLE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowToImproveScreen() {
    val context = LocalContext.current

    // 2. Curated Data (You can add more here)
    val resources = listOf(
        ImprovementResource(
            "Mastering the Big 3", "Jeff Nippard's guide to Bench, Squat, & Deadlift.",
            "https://www.youtube.com/watch?v=CAwf7n6Luuc", ResourceType.VIDEO, "Strength"
        ),
        ImprovementResource(
            "Sleep for Muscle Growth",
            "Why sleep is your #1 recovery tool.",
            "https://www.sleepfoundation.org/physical-health/athletic-performance-and-sleep",
            ResourceType.ARTICLE,
            "Recovery"
        ),
        ImprovementResource(
            "Zone 2 Cardio Explained", "Improve your endurance with low-intensity cardio.",
            "https://www.youtube.com/watch?v=m7H05F8bX_U", ResourceType.VIDEO, "Endurance"
        ),
        ImprovementResource(
            "Mindset of an Athlete", "How to stay disciplined when motivation fades.",
            "https://jamesclear.com/atomic-habits", ResourceType.ARTICLE, "Mindset"
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Growth Hub", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Curated resources to level up your life.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Group by category automatically
            val categories = resources.groupBy { it.category }

            categories.forEach { (category, items) ->
                item {
                    Text(
                        text = category.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Black
                    )
                }

                items(items) { resource ->
                    ResourceCard(resource) {
                        openUrl(context, resource.url)
                    }
                }
            }
        }
    }
}

@Composable
fun ResourceCard(resource: ImprovementResource, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (resource.type == ResourceType.VIDEO) Icons.Default.PlayCircle else Icons.Default.MenuBook,
                contentDescription = null,
                tint = if (resource.type == ResourceType.VIDEO) Color.Red else Color.Cyan,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(resource.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    resource.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Helper function to open YouTube or Web links
fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}