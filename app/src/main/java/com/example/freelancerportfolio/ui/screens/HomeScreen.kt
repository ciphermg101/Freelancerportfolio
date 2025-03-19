package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.freelancerportfolio.data.FreelancerViewModel
import com.example.freelancerportfolio.ui.components.FreelancerCard
import androidx.compose.runtime.livedata.observeAsState
import com.example.freelancerportfolio.data.UserPreferences
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FreelancerViewModel,
    navController: NavController,
    userPreferences: UserPreferences,
    onProfileClick: (Int) -> Unit
) {
    val allFreelancers by viewModel.allFreelancerProfiles.observeAsState(emptyList())
    val isDarkMode by userPreferences.getDarkMode.collectAsState(initial = false)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Freelancer Profiles", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    ThemeToggle(userPreferences, isDarkMode)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("form") },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(6.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Profile", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (allFreelancers.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No profiles found. Click + to add one.", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allFreelancers) { freelancer ->
                        FreelancerCard(
                            freelancer = freelancer,
                            onProfileClick = { onProfileClick(freelancer.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeToggle(userPreferences: UserPreferences, isDarkMode: Boolean) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Dark Mode")
        Switch(
            checked = isDarkMode,
            onCheckedChange = { enabled ->
                coroutineScope.launch {
                    userPreferences.setDarkMode(enabled)
                }
            }
        )
    }
}
