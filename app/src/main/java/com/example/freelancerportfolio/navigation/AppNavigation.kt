package com.example.freelancerportfolio.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.freelancerportfolio.data.FreelancerViewModel
import com.example.freelancerportfolio.data.UserPreferences
import com.example.freelancerportfolio.ui.screens.*

sealed class Screen(val route: String, val label: String, val icon: ImageVector, val iconOutlined: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    object Favorites : Screen("favorites", "Favorites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
    object Form : Screen("form", "Add", Icons.Filled.Add, Icons.Filled.Add)
}

@Composable
fun AppNavigation(viewModel: FreelancerViewModel, userPreferences: UserPreferences) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    navController = navController,
                    userPreferences = userPreferences, // ✅ Pass userPreferences
                    onProfileClick = { id -> navController.navigate("profile/$id") }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    viewModel = viewModel,
                    onProfileClick = { id -> navController.navigate("profile/$id") }
                )
            }
            composable(Screen.Form.route) {
                FreelancerFormScreen(
                    viewModel = viewModel,
                    onSave = { navController.popBackStack() }
                )
            }
            composable("profile/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                ProfileScreen(
                    viewModel = viewModel,
                    freelancerId = id,
                    onEdit = { freelancerId -> navController.navigate("editProfile/$freelancerId") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("editProfile/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                EditProfileScreen(
                    freelancerId = id ?: 0,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(Screen.Home, Screen.Favorites, Screen.Form)

    NavigationBar {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == screen.route) screen.icon else screen.iconOutlined,
                        contentDescription = screen.label
                    )
                },
                label = { Text(screen.label) }
            )
        }
    }
}
