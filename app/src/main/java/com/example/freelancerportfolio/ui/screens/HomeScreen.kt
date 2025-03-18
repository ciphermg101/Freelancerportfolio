package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.freelancerportfolio.data.FreelancerViewModel
import com.example.freelancerportfolio.ui.components.FreelancerCard

@Composable
fun HomeScreen(
    viewModel: FreelancerViewModel,
    onProfileClick: (Int) -> Unit // Ensure this matches `AppNavigation.kt`
) {
    // Search query state
    var searchQuery by remember { mutableStateOf("") }

    // Observe changes from ViewModel
    val allFreelancers by viewModel.allFreelancers.observeAsState(emptyList())
    val searchResults by remember(searchQuery) {
        derivedStateOf {
            if (searchQuery.isEmpty()) allFreelancers
            else viewModel.searchProfiles(searchQuery).value ?: emptyList()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by Role or Skill") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        if (searchResults.isEmpty()) {
            Text(
                text = "No portfolios found. Try adding or searching again.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(searchResults) { freelancer ->
                    FreelancerCard(
                        imagePath = freelancer.profilePicture ?: "", // Ensure image path safety
                        name = freelancer.name,
                        description = freelancer.summary,
                        onProfileClick = { onProfileClick(freelancer.id) }
                    )
                }
            }
        }
    }
}
