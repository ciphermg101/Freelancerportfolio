package com.example.freelancerportfolio.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import com.example.freelancerportfolio.data.FreelancerProfile

@Composable
fun FreelancerCard(
    freelancer: FreelancerProfile, // Pass the whole FreelancerProfile object
    onProfileClick: (FreelancerProfile) -> Unit // Pass the profile on click for navigation
) {
    // Load the image from the file system (if imagePath exists in the profile)
    val bitmap = rememberBitmapFromFile(freelancer.profilePicture ?: "")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onProfileClick(freelancer) } // Pass the profile when clicked
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            // If a valid bitmap is available, display it
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Freelancer Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            // Freelancer name and role title
            Text(text = freelancer.name, modifier = Modifier.padding(8.dp))
            Text(text = freelancer.roleTitle, modifier = Modifier.padding(8.dp))
            // Freelancer description or summary
            Text(text = freelancer.summary, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun rememberBitmapFromFile(filePath: String): Bitmap? {
    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    LaunchedEffect(filePath) {
        bitmap = loadBitmapFromFile(filePath)
    }
    return bitmap
}

suspend fun loadBitmapFromFile(filePath: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val file = File(filePath)
            if (file.exists()) {
                BitmapFactory.decodeFile(filePath)
            } else {
                Log.e("FreelancerCard", "File not found: $filePath")
                null
            }
        } catch (e: Exception) {
            Log.e("FreelancerCard", "Error loading image", e)
            null
        }
    }
}
