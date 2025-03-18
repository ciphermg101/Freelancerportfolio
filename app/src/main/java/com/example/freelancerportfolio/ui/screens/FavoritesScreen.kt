package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.freelancerportfolio.data.FreelancerViewModel
import com.example.freelancerportfolio.ui.components.FreelancerCard

@Composable
fun FavoritesScreen(
    viewModel: FreelancerViewModel,
    onProfileClick: (Int) -> Unit // Ensure this parameter is included
) {
    // Observe favorite freelancers list from ViewModel
    val favorites by viewModel.favoriteFreelancers.observeAsState(emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(favorites) { freelancer ->
            FreelancerCard(
                imagePath = freelancer.profilePicture ?: "",
                name = freelancer.name,
                description = freelancer.summary,
                onProfileClick = { onProfileClick(freelancer.id) } // Pass onProfileClick correctly
            )
        }
    }
}

