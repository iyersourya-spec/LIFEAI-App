package com.lifeai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Feature(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LifeAIApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifeAIApp() {

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("LIFEAI", fontWeight = FontWeight.Bold)
                        Text("Your everyday AI hub", fontSize = 12.sp)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        },

        bottomBar = {
            NavigationBar {

                val icons = listOf(
                    Icons.Default.Home,
                    Icons.Default.AutoAwesome,
                    Icons.Default.Headphones,
                    Icons.Default.Person
                )

                val labels = listOf("Home", "AI", "Stories", "Profile")

                icons.forEachIndexed { index, icon ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(icon, contentDescription = labels[index]) },
                        label = { Text(labels[index]) }
                    )
                }
            }
        }

    ) { padding ->

        when (selectedTab) {
            0 -> HomeScreen(padding)
            1 -> AIAssistantScreen(padding)
            2 -> StoriesScreen(padding)
            3 -> ProfileScreen(padding)
        }
    }
}

@Composable
fun HomeScreen(padding: PaddingValues) {

    var search by remember { mutableStateOf("") }

    val features = listOf(
        Feature("AI Assistant", "Ask questions and get help", Icons.Default.AutoAwesome),
        Feature("Study Center", "Learn and solve academic tasks", Icons.Default.School),
        Feature("Stories", "Listen to original stories", Icons.Default.Headphones),
        Feature("Local", "Discover nearby services", Icons.Default.Place),
        Feature("Tools", "Calculator, notes and more", Icons.Default.Build),
        Feature("Courses", "Track your learning", Icons.Default.MenuBook),
        Feature("Travel", "Plan trips and places", Icons.Default.TravelExplore),
        Feature("Creative", "Ideas, scripts and prompts", Icons.Default.Brush)
    )

    val filtered = features.filter {
        it.title.contains(search, ignoreCase = true) ||
            it.subtitle.contains(search, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search anything...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(Modifier.padding(20.dp)) {

                    Text(
                        "What do you need today?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text("Ask LIFEAI, learn, create, discover or use daily tools.")

                    Spacer(Modifier.height(14.dp))

                    Button(onClick = {}) {
                        Icon(Icons.Default.AutoAwesome, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Ask LIFEAI")
                    }
                }
            }
        }

        item {
            Text("Quick access", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        items(filtered) { feature ->
            FeatureCard(feature)
        }

        item {
            RedeemCodeCard()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureCard(feature: Feature) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        onClick = {}
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(feature.icon, null, modifier = Modifier.size(32.dp))

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(feature.title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(feature.subtitle, fontSize = 13.sp)
            }

            Icon(Icons.Default.ChevronRight, null)
        }
    }
}

@Composable
fun RedeemCodeCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Text("Free Fire Codes", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(Modifier.height(6.dp))

            Text("Legitimate publicly announced redeem-code information.")

            Spacer(Modifier.height(10.dp))

            OutlinedButton(onClick = {}) {
                Text("View code information")
            }
        }
    }
}

@Composable
fun AIAssistantScreen(padding: PaddingValues) {

    var prompt by remember { mutableStateOf("") }

    var answer by remember {
        mutableStateOf(
            "Hi! I'm LIFEAI. Connect an AI API to make this assistant respond to real questions."
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {

        Text("AI Assistant", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(answer, modifier = Modifier.padding(18.dp))
        }

        Spacer(Modifier.weight(1f))

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ask LIFEAI anything...") },
            minLines = 2
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                answer = if (prompt.isBlank()) {
                    "Please type a question first."
                } else {
                    "You asked: $prompt\n\nConnect your AI backend/API here for a real answer."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Send, null)
            Spacer(Modifier.width(8.dp))
            Text("Ask")
        }
    }
}

@Composable
fun StoriesScreen(padding: PaddingValues) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text("Stories", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Original or properly licensed stories only.")
            Spacer(Modifier.height(8.dp))
        }

        items(listOf("The Last Train", "A New Beginning", "The Hidden Village")) { title ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(Modifier.padding(18.dp)) {

                    Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                    Spacer(Modifier.height(8.dp))

                    Text("Original LIFEAI story.")

                    Spacer(Modifier.height(10.dp))

                    Button(onClick = {}) {
                        Icon(Icons.Default.PlayArrow, null)
                        Spacer(Modifier.width(6.dp))
                        Text("Listen")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(padding: PaddingValues) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp)
    ) {

        Text("Profile", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(20.dp))

        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Settings")
        }

        Spacer(Modifier.height(8.dp))

        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Privacy")
        }
    }
}
