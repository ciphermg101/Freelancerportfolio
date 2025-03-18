package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState // Add this import
import com.example.freelancerportfolio.data.Certification
import com.example.freelancerportfolio.data.FreelancerViewModel

@OptIn(ExperimentalMaterial3Api::class) // Opt-in to experimental Material3 API if needed
@Composable
fun EditProfileScreen(
    freelancerId: Int,
    viewModel: FreelancerViewModel = viewModel(),
    onBack: () -> Unit
) {
    // Observe the freelancer profile using LiveData
    val freelancer by viewModel.getFreelancerProfileById(freelancerId).observeAsState(initial = null)

    // Fields for editing the freelancer's profile
    var name by remember { mutableStateOf("") }
    var roleTitle by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    var certifications by remember { mutableStateOf("") }
    var languages by remember { mutableStateOf("") }
    var socialLinks by remember { mutableStateOf("") }
    var portfolioLinks by remember { mutableStateOf("") }
    var hourlyRate by remember { mutableDoubleStateOf(0.0) } // Use mutableDoubleStateOf for hourly rate
    var availability by remember { mutableStateOf(false) }

    // Pre-fill fields when the profile is loaded
    LaunchedEffect(freelancer) {
        freelancer?.let {
            name = it.name
            roleTitle = it.roleTitle
            email = it.email
            phone = it.phone
            location = it.location
            summary = it.summary
            skills = it.skills.joinToString(", ")
            certifications = it.certifications.joinToString(", ") { cert -> cert.title }
            languages = it.languages.joinToString(", ")
            socialLinks = it.socialLinks.joinToString(", ")
            portfolioLinks = it.portfolioLinks.joinToString(", ")
            hourlyRate = it.hourlyRate
            availability = it.availability
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profile") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = roleTitle,
                onValueChange = { roleTitle = it },
                label = { Text("Role Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = summary,
                onValueChange = { summary = it },
                label = { Text("Summary") },
                modifier = Modifier.fillMaxWidth()
            )

            // Add fields for skills, certifications, languages, social links, and portfolio links
            OutlinedTextField(
                value = skills,
                onValueChange = { skills = it },
                label = { Text("Skills") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = certifications,
                onValueChange = { certifications = it },
                label = { Text("Certifications") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = languages,
                onValueChange = { languages = it },
                label = { Text("Languages") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = socialLinks,
                onValueChange = { socialLinks = it },
                label = { Text("Social Links") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = portfolioLinks,
                onValueChange = { portfolioLinks = it },
                label = { Text("Portfolio Links") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        freelancer?.let {
                            val updatedProfile = it.copy(
                                name = name,
                                roleTitle = roleTitle,
                                email = email,
                                phone = phone,
                                location = location,
                                summary = summary,
                                skills = skills.split(", ").map { it.trim() },
                                certifications = certifications.split(", ").map { Certification(it, "", 0) },
                                languages = languages.split(", ").map { it.trim() },
                                socialLinks = socialLinks.split(", ").map { it.trim() },
                                portfolioLinks = portfolioLinks.split(", ").map { it.trim() },
                                hourlyRate = hourlyRate,
                                availability = availability
                            )
                            viewModel.update(updatedProfile)
                            onBack()
                        }
                    }
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}
