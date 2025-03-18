package com.example.freelancerportfolio.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.livedata.observeAsState
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.remember
import com.example.freelancerportfolio.R
import com.example.freelancerportfolio.data.FreelancerProfile
import com.example.freelancerportfolio.data.FreelancerViewModel

@Composable
fun ProfileScreen(
    viewModel: FreelancerViewModel,
    freelancerId: Int?,
    onEdit: (Int) -> Unit, // Navigate to edit screen
    onBack: () -> Unit
) {
    val freelancer by viewModel.getFreelancerProfileById(freelancerId ?: 0).observeAsState(initial = null)

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                selectedImageUri = it.toString()
                // Optionally update profile with new image
                viewModel.updateProfileImage(freelancerId ?: 0, selectedImageUri!!)
            }
        }
    )

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Portfolio", style = MaterialTheme.typography.titleMedium) },
            text = { Text("Are you sure you want to delete this portfolio? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        freelancer?.let {
                            viewModel.delete(it)
                            onBack()
                        }
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    freelancer?.let { profile ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Image
                    ProfileImage(profile.profilePicture ?: selectedImageUri)

                    // Button to select image
                    OutlinedButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text("Change Profile Image")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = profile.roleTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("📍 ${profile.location}", style = MaterialTheme.typography.bodyMedium)

                    // Favorite status toggle
                    IconButton(
                        onClick = {
                            viewModel.toggleFavorite(profile.id)
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (profile.isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = if (profile.isFavorite) "Remove from favorites" else "Add to favorites"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contact Information
            ContactInfo(profile)

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(horizontalArrangement = Arrangement.Center) {
                // Edit Profile Button
                FilledIconButton(
                    onClick = { onEdit(profile.id) }, // Navigate to Edit screen
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Profile")
                }

                // Share Profile Button
                FilledIconButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Check out my portfolio!")
                            putExtra(Intent.EXTRA_TEXT, buildShareText(profile))
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Portfolio"))
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(Icons.Filled.Share, contentDescription = "Share")
                }

                // Delete Profile Button
                FilledIconButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.padding(8.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Back Button
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to List")
            }
        }
    } ?: Text("Portfolio not found", style = MaterialTheme.typography.headlineSmall)
}

@Composable
fun ProfileImage(imagePath: String?) {
    val painter = rememberAsyncImagePainter(model = imagePath ?: R.drawable.ic_launcher_foreground)
    Image(
        painter = painter,
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
    )
}

@Composable
fun ContactInfo(profile: FreelancerProfile) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Email, contentDescription = "Email", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = profile.email, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Phone, contentDescription = "Phone", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = profile.phone, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private fun buildShareText(profile: FreelancerProfile): String {
    return """
        Check out my portfolio for ${profile.roleTitle}:
        Name: ${profile.name}
        Email: ${profile.email}
        Summary: ${profile.summary}
        
        Contact me if you're interested in hiring!
    """.trimIndent()
}
