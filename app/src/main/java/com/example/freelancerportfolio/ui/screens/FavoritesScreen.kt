package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.freelancerportfolio.data.FreelancerProfile
import com.example.freelancerportfolio.data.FreelancerViewModel
import com.example.freelancerportfolio.ui.components.FreelancerCard

@Composable
fun FavoritesScreen(
    viewModel: FreelancerViewModel,
    onProfileClick: (FreelancerProfile) -> Unit // Ensure this parameter is included
) {
    // Observe favorite freelancers list from ViewModel
    val favorites by viewModel.favoriteFreelancerProfiles.observeAsState(emptyList()) // Updated property

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(favorites) { freelancer ->
            // Pass the entire freelancer profile to FreelancerCard
            FreelancerCard(
                freelancer = freelancer,  // Pass the entire FreelancerProfile object
                onProfileClick = { onProfileClick(freelancer) } // Pass the profile on click
            )
        }
    }
}
