package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.freelancerportfolio.data.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment


@Composable
fun FreelancerFormScreen(
    viewModel: FreelancerViewModel,
    onSave: () -> Unit
) {
    // State variables
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var phone by remember { mutableStateOf(TextFieldValue("")) }
    var roleTitle by remember { mutableStateOf(TextFieldValue("")) }
    var summary by remember { mutableStateOf(TextFieldValue("")) }
    var hourlyRate by remember { mutableStateOf(TextFieldValue("0.0")) }
    var availability by remember { mutableStateOf(true) }
    var agreedToTerms by remember { mutableStateOf(false) }

    // For demonstration: a radio group for “Experience Level”
    val experienceLevels = listOf("Junior", "Mid", "Senior")
    var selectedExperienceLevel by remember { mutableStateOf(experienceLevels[0]) }

    // A sample to illustrate Switch, CheckBox, RadioButtons, etc.
    val coroutineScope = rememberCoroutineScope()

    // AlertDialog state
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    // A mock function to simulate importing from Credly
    fun mockImportFromCredly(): List<Certification> {
        // In a real scenario, you'd make a network call
        return listOf(
            Certification("React Certification", "Credly", 2022),
            Certification("Node.js Certification", "Credly", 2021)
        )
    }

    // Collect user input for final submission
    fun handleSave() {
        // Basic validation
        if (name.text.isBlank()) {
            alertMessage = "Name is required."
            showAlert = true
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            alertMessage = "Please provide a valid email."
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
            skills = listOf(selectedExperienceLevel), // For demonstration
            certifications = mockImportFromCredly(),  // Mock import
            experience = emptyList(),
            education = emptyList(),
            projects = emptyList(),
            testimonials = emptyList(),
            languages = listOf("English"), // Hardcoded for demo
            location = "Unknown",
            socialLinks = emptyList(),
            portfolioLinks = emptyList(),
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = "Create a New Portfolio", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
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
            value = roleTitle,
            onValueChange = { roleTitle = it },
            label = { Text("Role Title (e.g., React Developer)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = summary,
            onValueChange = { summary = it },
            label = { Text("Summary") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = hourlyRate,
            onValueChange = { hourlyRate = it },
            label = { Text("Hourly Rate") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Switch for availability
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Availability:")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(checked = availability, onCheckedChange = { availability = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Radio buttons for experience level
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

        // CheckBox for “I agree to terms”
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
            Text("Save Portfolio")
        }
    }
}
