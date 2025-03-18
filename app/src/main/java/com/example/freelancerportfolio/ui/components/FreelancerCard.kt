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

@Composable
fun FreelancerCard(
    imagePath: String,
    name: String,
    description: String,
    onProfileClick: () -> Unit // Added parameter for navigation click
) {
    // Loads the bitmap in the background
    val bitmap = rememberBitmapFromFile(imagePath)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onProfileClick() } // Make card clickable
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Freelancer Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Text(text = name, modifier = Modifier.padding(8.dp))
            Text(text = description, modifier = Modifier.padding(8.dp))
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
