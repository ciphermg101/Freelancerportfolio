package com.example.freelancerportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.freelancerportfolio.data.FreelancerViewModel
import com.example.freelancerportfolio.navigation.AppNavigation
import com.example.freelancerportfolio.ui.theme.FreelancerportfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreelancerportfolioTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val viewModel = FreelancerViewModel(application)
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}
