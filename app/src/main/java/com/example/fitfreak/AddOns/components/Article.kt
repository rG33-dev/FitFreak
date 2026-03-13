package com.example.fitfreak.AddOns.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


import android.net.Uri
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Scalable Data Model
data class Article(
    val title: String,
    val category: String,
    val readTime: String,
    val url: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen() {
    val context = LocalContext.current

    // 2. State Management for Search and Filtering
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "STRENGTH", "NUTRITION", "CARDIO", "PSYCHOLOGY", "RECOVERY")

    val allArticles = listOf(
        Article("Mastering the Deadlift", "STRENGTH", "5 min", "https://example.com/deadlift", "Proper form and cues for the king of lifts."),
        Article("Optimal Protein Intake", "NUTRITION", "8 min", "https://example.com/protein", "How much do you actually need for hypertrophy?"),
        Article("The Science of HIIT", "CARDIO", "6 min", "https://example.com/hiit", "Maximizing fat loss in minimum time."),
        Article("Mindset of a Freak", "PSYCHOLOGY", "4 min", "https://example.com/mindset", "Developing the mental grit of elite athletes."),
        Article("Sleep & Hypertrophy", "RECOVERY", "7 min", "https://example.com/sleep", "Why your gains happen in bed, not the gym."),
        Article("Creatine Guide", "NUTRITION", "5 min", "https://example.com/creatine", "The most researched supplement explained.")
    )

    // 3. Filter Logic
    val filteredArticles = allArticles.filter {
        (selectedCategory == "All" || it.category == selectedCategory) &&
                (it.title.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 16.dp)
    ) {
        Text(
            text = "ELITE ARTICLES",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // 4. Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search articles...", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF00E5FF),
                unfocusedBorderColor = Color.DarkGray,
                focusedContainerColor = Color(0xFF121212),
                unfocusedContainerColor = Color(0xFF121212)
            )
        )

        // 5. Horizontal Category Filter
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCategory == category
                Surface(
                    modifier = Modifier.clickable { selectedCategory = category },
                    shape = CircleShape,
                    color = if (isSelected) Color(0xFF00E5FF) else Color(0xFF1A1A1A),
                    border = if (isSelected) null else BorderStroke(1.dp, Color.DarkGray)
                ) {
                    Text(
                        text = category,
                        color = if (isSelected) Color.Black else Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 6. Scalable List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredArticles) { article ->
                ArticleCard(article) {
                    // 7. Intent Logic to open browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                    context.startActivity(intent)
                }
            }

            // Empty State
            if (filteredArticles.isEmpty()) {
                item {
                    Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No articles found.", color = Color.Gray)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(0.5.dp, Color.DarkGray)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00E5FF))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        article.category,
                        color = Color(0xFF00E5FF),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    article.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    article.description,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = "⏱ ${article.readTime} read",
                    color = Color.DarkGray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}