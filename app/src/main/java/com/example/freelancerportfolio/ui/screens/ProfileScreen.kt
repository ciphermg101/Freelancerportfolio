package com.example.freelancerportfolio.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.example.freelancerportfolio.R
import com.example.freelancerportfolio.data.FreelancerProfile
import com.example.freelancerportfolio.data.FreelancerViewModel

@Composable
fun ProfileScreen(
    viewModel: FreelancerViewModel,
    freelancerId: Int?,
    onBack: () -> Unit
) {
    val freelancerIdInt = freelancerId ?: 0 // Ensure freelancerId is always an Int

    // Observe the freelancer profile safely
    val freelancer by viewModel
        .getFreelancerById(freelancerIdInt)
        .observeAsState(initial = null)

    val context = LocalContext.current
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Portfolio") },
            text = { Text("Are you sure you want to delete this portfolio?") },
            confirmButton = {
                TextButton(onClick = {
                    freelancer?.let {
                        viewModel.delete(it)
                        onBack()
                    }
                    showDeleteDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        if (freelancer == null) {
            // If the freelancer is still loading or doesn't exist
            Text("Portfolio not found", style = MaterialTheme.typography.titleMedium)
            Button(onClick = onBack) {
                Text("Back")
            }
        } else {
            // Use let to ensure freelancer is non-null
            freelancer?.let { profile ->
                Text(text = profile.roleTitle, style = MaterialTheme.typography.titleLarge)
                Text(text = profile.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // Display circular image (mock)
                val imagePainter = rememberAsyncImagePainter(
                    model = profile.profilePicture ?: R.drawable.ic_launcher_foreground
                )

                Image(
                    painter = imagePainter,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.large)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Email: ${profile.email}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Phone: ${profile.phone}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Location: ${profile.location}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Summary:", style = MaterialTheme.typography.titleSmall)
                Text(profile.summary, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    FilledIconButton(
                        onClick = {
                            // Share intent only if freelancer is available
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Check out my portfolio!")
                                putExtra(Intent.EXTRA_TEXT, buildShareText(profile))
                            }
                            context.startActivity(
                                Intent.createChooser(shareIntent, "Share Portfolio")
                            )
                        },
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = "Share")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    FilledIconButton(
                        onClick = { showDeleteDialog = true }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onBack) {
                    Text("Back to List")
                }
            }
        }
    }
}

private fun buildShareText(profile: FreelancerProfile): String {
    return """
        Check out my portfolio for ${profile.roleTitle}:
        Name: ${profile.name}
        Email: ${profile.email}
        Summary: ${profile.summary}
        Skills: ${profile.skills.joinToString(", ")}
        
        Contact me if you're interested in hiring!
    """.trimIndent()
}
