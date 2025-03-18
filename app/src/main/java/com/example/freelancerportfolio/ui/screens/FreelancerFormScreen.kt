package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.freelancerportfolio.data.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreelancerFormScreen(
    viewModel: FreelancerViewModel,
    onSave: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    // State variables
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var phone by remember { mutableStateOf(TextFieldValue("")) }
    var roleTitle by remember { mutableStateOf(TextFieldValue("")) }
    var summary by remember { mutableStateOf(TextFieldValue("")) }
    var hourlyRate by remember { mutableStateOf(TextFieldValue("0.0")) }
    var availability by remember { mutableStateOf(true) }
    var agreedToTerms by remember { mutableStateOf(false) }

    // New fields
    var skills by remember { mutableStateOf(TextFieldValue("")) }
    var certifications by remember { mutableStateOf(TextFieldValue("")) }
    var languages by remember { mutableStateOf(TextFieldValue("English")) }
    var socialLinks by remember { mutableStateOf(TextFieldValue("")) }
    var portfolioLinks by remember { mutableStateOf(TextFieldValue("")) }

    // Experience level radio buttons
    val experienceLevels = listOf("Junior", "Mid", "Senior")
    var selectedExperienceLevel by remember { mutableStateOf(experienceLevels[0]) }

    // AlertDialog state
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    // Function to validate input and save the profile
    fun handleSave() {
        if (name.text.isBlank()) {
            alertMessage = "Name is required."
            showAlert = true
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            alertMessage = "Please enter a valid email."
            showAlert = true
            return
        }
        if (!agreedToTerms) {
            alertMessage = "You must agree to the terms before creating a portfolio."
            showAlert = true
            return
        }

        val newProfile = FreelancerProfile(
            name = name.text,
            email = email.text,
            phone = phone.text,
            roleTitle = roleTitle.text,
            summary = summary.text,
            hourlyRate = hourlyRate.text.toDoubleOrNull() ?: 0.0,
            availability = availability,
            skills = skills.text.split(",").map { it.trim() },
            certifications = certifications.text.split(",").map { Certification(it, "Self-Learned", 2024) },
            experience = emptyList(),
            education = emptyList(),
            projects = emptyList(),
            testimonials = emptyList(),
            languages = languages.text.split(",").map { it.trim() },
            location = "Unknown",
            socialLinks = socialLinks.text.split(","),
            portfolioLinks = portfolioLinks.text.split(","),
            isFavorite = false
        )

        coroutineScope.launch {
            viewModel.insert(newProfile)
            onSave()
        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text("Validation Error") },
            text = { Text(alertMessage) },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Create Freelancer Portfolio") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = name, onValueChange = { name = it },
                    label = { Text("Name") }, modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    label = { Text("Email") }, modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phone, onValueChange = { phone = it },
                    label = { Text("Phone") }, modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = roleTitle, onValueChange = { roleTitle = it },
                    label = { Text("Role Title") }, modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = summary, onValueChange = { summary = it },
                    label = { Text("Summary") }, modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = hourlyRate, onValueChange = { hourlyRate = it },
                    label = { Text("Hourly Rate") }, modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Availability Toggle
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Availability:")
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(checked = availability, onCheckedChange = { availability = it })
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Experience Level Radio Buttons
                Text("Experience Level:")
                experienceLevels.forEach { level ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (level == selectedExperienceLevel),
                            onClick = { selectedExperienceLevel = level }
                        )
                        Text(level)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Additional Fields
                OutlinedTextField(
                    value = skills, onValueChange = { skills = it },
                    label = { Text("Skills (comma-separated)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = certifications, onValueChange = { certifications = it },
                    label = { Text("Certifications (comma-separated)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = languages, onValueChange = { languages = it },
                    label = { Text("Languages (comma-separated)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = socialLinks, onValueChange = { socialLinks = it },
                    label = { Text("Social Links (comma-separated)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = portfolioLinks, onValueChange = { portfolioLinks = it },
                    label = { Text("Portfolio Links (comma-separated)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Terms and Conditions Checkbox
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = agreedToTerms,
                        onCheckedChange = { agreedToTerms = it }
                    )
                    Text("I agree to the terms and conditions.")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { handleSave() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Portfolio")
                }
            }
        }
    )
}
