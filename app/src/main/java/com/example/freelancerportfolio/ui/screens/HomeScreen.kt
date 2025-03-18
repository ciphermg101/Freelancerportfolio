package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.freelancerportfolio.data.FreelancerViewModel
import com.example.freelancerportfolio.ui.components.FreelancerCard
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun HomeScreen(
    viewModel: FreelancerViewModel,
    navController: NavController, // Add the NavController to this function
    onProfileClick: (Int) -> Unit
) {
    // Observe all freelancer profiles from ViewModel using LiveData
    val allFreelancers by viewModel.allFreelancerProfiles.observeAsState(emptyList()) // Observe LiveData

    // Column to display freelancer profiles
    Box(modifier = Modifier.fillMaxSize()) {
        // LazyColumn to display all freelancer profiles with a nice top margin
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 70.dp) // Added top padding for a cleaner look
        ) {
            items(allFreelancers) { freelancer ->
                // Use the FreelancerCard component to display the freelancer profile
                FreelancerCard(
                    freelancer = freelancer,  // Pass the whole FreelancerProfile object
                    onProfileClick = { onProfileClick(freelancer.id) } // Pass the freelancer ID when clicked
                )
            }
        }

        // Floating action button to add a new profile, with better styling
        FloatingActionButton(
            onClick = {
                // Navigate to the FreelancerFormScreen when clicked
                navController.navigate("form")
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd), // Position it at the bottom-right
            containerColor = MaterialTheme.colorScheme.primary, // Customize the color
            elevation = FloatingActionButtonDefaults.elevation(6.dp) // Add shadow for elevation
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Profile",
                tint = Color.White // Ensure the icon is visible against the button
            )
        }

        // Optional: Add a title or section header to make the screen feel more structured
        Text(
            text = "Freelancer Profiles",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart), // Position at the top-left
            color = MaterialTheme.colorScheme.onBackground // Style based on theme
        )
    }
}
