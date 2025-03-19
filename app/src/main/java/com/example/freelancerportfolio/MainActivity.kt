package com.example.freelancerportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.freelancerportfolio.data.FreelancerViewModel
import com.example.freelancerportfolio.data.UserPreferences
import com.example.freelancerportfolio.navigation.AppNavigation
import com.example.freelancerportfolio.ui.theme.FreelancerPortfolioTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Manually create UserPreferences instance
        val userPreferences = UserPreferences(applicationContext)

        // Manually create ViewModel instance
        val viewModel = FreelancerViewModel(application)

        lifecycleScope.launch {
            userPreferences.getDarkMode.collect { isDarkMode ->
                setContent {
                    FreelancerPortfolioTheme(isDarkMode = isDarkMode) {
                        AppNavigation(viewModel = viewModel, userPreferences = userPreferences)
                    }
                }
            }
        }
    }
}
