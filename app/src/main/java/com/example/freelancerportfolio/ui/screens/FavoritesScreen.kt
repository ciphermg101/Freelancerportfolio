package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.freelancerportfolio.data.FreelancerProfile
import com.example.freelancerportfolio.data.FreelancerViewModel
import com.example.freelancerportfolio.ui.components.FreelancerCard

@Composable
fun FavoritesScreen(
    viewModel: FreelancerViewModel,
    onProfileClick: (FreelancerProfile) -> Unit
) {
    val favorites by viewModel.favoriteFreelancerProfiles.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        // Title
        Text(
            text = "Favorite Profiles",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(16.dp)
        )

        // List of favorite freelancer profiles
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(favorites) { freelancer ->
                FreelancerCard(
                    freelancer = freelancer,
                    onProfileClick = { onProfileClick(freelancer) }
                )
            }
        }
    }
}
