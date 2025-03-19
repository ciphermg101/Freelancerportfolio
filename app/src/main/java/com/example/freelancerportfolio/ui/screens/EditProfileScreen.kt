package com.example.freelancerportfolio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.freelancerportfolio.data.Certification
import com.example.freelancerportfolio.data.FreelancerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    freelancerId: Int,
    viewModel: FreelancerViewModel = viewModel(),
    onBack: () -> Unit
) {
    val freelancer by viewModel.getFreelancerProfileById(freelancerId).observeAsState()
    var state by remember { mutableStateOf(freelancer) }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var showSaveDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    LaunchedEffect(freelancer) {
        state = freelancer
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { showCancelDialog = true }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { showCancelDialog = true }) {
                        Text("Cancel")
                    }
                    Button(onClick = { showSaveDialog = true }) {
                        Text("Save Changes")
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {
            state?.let { profile ->
                InputField("Name", profile.name) { state = profile.copy(name = it) }
                InputField("Role Title", profile.roleTitle) { state = profile.copy(roleTitle = it) }
                InputField("Email", profile.email) { state = profile.copy(email = it) }
                InputField("Phone", profile.phone) { state = profile.copy(phone = it) }
                InputField("Location", profile.location) { state = profile.copy(location = it) }
                InputField("Summary", profile.summary) { state = profile.copy(summary = it) }
                InputField("Skills", profile.skills.joinToString(", ")) {
                    state = profile.copy(skills = it.split(", ").map(String::trim))
                }
                InputField("Certifications", profile.certifications.joinToString(", ") { it.title }) {
                    state = profile.copy(certifications = it.split(", ").map { cert -> Certification(cert, "", 0) })
                }
                InputField("Languages", profile.languages.joinToString(", ")) {
                    state = profile.copy(languages = it.split(", ").map(String::trim))
                }
                InputField("Social Links", profile.socialLinks.joinToString(", ")) {
                    state = profile.copy(socialLinks = it.split(", ").map(String::trim))
                }
                InputField("Portfolio Links", profile.portfolioLinks.joinToString(", ")) {
                    state = profile.copy(portfolioLinks = it.split(", ").map(String::trim))
                }
            }
        }
    }

    // Save Confirmation Dialog
    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Confirm Save") },
            text = { Text("Are you sure you want to save your changes?") },
            confirmButton = {
                Button(
                    onClick = {
                        state?.let { updatedProfile ->
                            viewModel.update(updatedProfile)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Profile updated successfully")
                            }
                        }
                        showSaveDialog = false
                        onBack()
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Cancel Confirmation Dialog
    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Discard Changes?") },
            text = { Text("Any unsaved changes will be lost. Are you sure?") },
            confirmButton = {
                Button(onClick = onBack) {
                    Text("Yes, Discard")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}
