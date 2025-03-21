package com.example.freelancerportfolio.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "freelancer_profile")
@TypeConverters(Converters::class)
data class FreelancerProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Basic identity
    val name: String,
    val email: String,
    val phone: String,

    // A new field for the specific role/title (e.g., "React Developer")
    val roleTitle: String,

    // Skills, certifications, experiences, etc.
    val skills: List<String>,
    val certifications: List<Certification> = emptyList(), // Default empty list
    val experience: List<JobExperience> = emptyList(), // Default empty list
    val education: List<Education> = emptyList(), // Default empty list
    val projects: List<Project> = emptyList(), // Default empty list
    val testimonials: List<Testimonial> = emptyList(), // Default empty list

    // Additional profile info
    val summary: String,
    val hourlyRate: Double,
    val availability: Boolean,
    val languages: List<String> = emptyList(), // Default empty list
    val location: String = "Unknown", // Default location value
    val socialLinks: List<String> = emptyList(), // Default empty list
    val portfolioLinks: List<String> = emptyList(), // Default empty list

    // UI flags
    val isFavorite: Boolean = false,

    // Store as URI or base64 for real images. For now, keep Int? as a mock.
    val profilePicture: String? = null
)

// Work experience model
data class JobExperience(
    val title: String,
    val company: String,
    val startDate: String,
    val endDate: String?,
    val description: String
)

// Education model
data class Education(
    val degree: String,
    val institution: String,
    val yearOfCompletion: Int
)

// Certification model
data class Certification(
    val title: String,
    val issuingOrganization: String,
    val yearObtained: Int
)

// Testimonial model
data class Testimonial(
    val clientName: String,
    val feedback: String,
    val rating: Double
)

// Project model
data class Project(
    val title: String,
    val description: String,
    val technologiesUsed: List<String>,
    val projectLink: String?
)
