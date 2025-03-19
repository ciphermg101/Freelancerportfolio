package com.example.freelancerportfolio.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.livedata.observeAsState
import com.example.freelancerportfolio.R
import com.example.freelancerportfolio.data.FreelancerProfile
import com.example.freelancerportfolio.data.FreelancerViewModel

@Composable
fun ProfileScreen(
    viewModel: FreelancerViewModel,
    freelancerId: Int?,
    onEdit: (Int) -> Unit,
    onBack: () -> Unit
) {
    val freelancer by viewModel.getFreelancerProfileById(freelancerId ?: 0).observeAsState()
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<String?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Observe profile image updates
    LaunchedEffect(freelancer?.profilePicture) {
        selectedImageUri = freelancer?.profilePicture
    }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                selectedImageUri = it.toString()
                viewModel.updateProfileImage(freelancerId ?: 0, selectedImageUri!!)
            }
        }
    )

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

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileImage(imagePath = selectedImageUri)

                    OutlinedButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text("Change Profile Image")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(profile.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text(profile.roleTitle, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("\uD83D\uDCCD ${profile.location}", style = MaterialTheme.typography.bodyMedium)

                    // Favorite toggle
                    IconButton(
                        onClick = { viewModel.toggleFavorite(profile.id) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (profile.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (profile.isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (profile.isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            ContactInfo(profile)
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                FilledIconButton(onClick = { onEdit(profile.id) }, modifier = Modifier.padding(8.dp)) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Profile")
                }
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
                FilledIconButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.padding(8.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Back to List")
            }
        }
    }
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

private fun buildShareText(profile: FreelancerProfile): String =
    """
        Check out my portfolio for ${profile.roleTitle}:
        Name: ${profile.name}
        Email: ${profile.email}
        Summary: ${profile.summary}
    """.trimIndent()