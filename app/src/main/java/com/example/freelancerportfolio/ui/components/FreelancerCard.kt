package com.example.freelancerportfolio.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import com.example.freelancerportfolio.data.FreelancerProfile

@Composable
fun FreelancerCard(
    freelancer: FreelancerProfile,
    onProfileClick: (Int) -> Unit
) {
    val bitmap = rememberBitmapFromFile(freelancer.profilePicture)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onProfileClick(freelancer.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Freelancer Image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(50)) // Circular image
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(freelancer.name, style = MaterialTheme.typography.titleMedium)
                Text(freelancer.roleTitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = freelancer.summary,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun rememberBitmapFromFile(filePath: String?): Bitmap? {
    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    LaunchedEffect(filePath) {
        filePath?.let {
            bitmap = loadBitmapFromFile(it)
        }
    }
    return bitmap
}

suspend fun loadBitmapFromFile(filePath: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val file = File(filePath)
            if (file.exists()) BitmapFactory.decodeFile(filePath)
            else null.also { Log.e("FreelancerCard", "File not found: $filePath") }
        } catch (e: Exception) {
            Log.e("FreelancerCard", "Error loading image", e)
            null
        }
    }
}
