package com.example.freelancerportfolio.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FreelancerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FreelancerRepository
    val allFreelancerProfiles: LiveData<List<FreelancerProfile>>
    val favoriteFreelancerProfiles: LiveData<List<FreelancerProfile>>

    init {
        val dao = FreelancerDatabase.getDatabase(application).freelancerDao()
        repository = FreelancerRepository(dao)
        allFreelancerProfiles = repository.allFreelancerProfiles
        favoriteFreelancerProfiles = repository.favoriteFreelancerProfiles
    }

    // Insert a new freelancer profile
    fun insert(profile: FreelancerProfile) = viewModelScope.launch {
        repository.insert(profile)
    }

    // Update an existing freelancer profile
    fun update(profile: FreelancerProfile) = viewModelScope.launch {
        repository.update(profile)
    }

    // Delete a freelancer profile
    fun delete(profile: FreelancerProfile) = viewModelScope.launch {
        repository.delete(profile)
    }

    // Update the profile picture of a freelancer profile
    fun updateProfileImage(id: Int, newImageUri: String) = viewModelScope.launch {
        val currentProfile = repository.getFreelancerProfileById(id).value
        currentProfile?.let {
            val updatedProfile = it.copy(profilePicture = newImageUri)
            repository.update(updatedProfile)
        }
    }

    // Get a freelancer profile by ID
    fun getFreelancerProfileById(id: Int): LiveData<FreelancerProfile> {
        return repository.getFreelancerProfileById(id)
    }

    // Update certifications in the freelancer profile
    fun updateCertifications(id: Int, newCertifications: List<Certification>) = viewModelScope.launch {
        val freelancer = repository.getFreelancerProfileById(id).value
        freelancer?.let {
            val updatedProfile = it.copy(certifications = newCertifications)
            repository.update(updatedProfile)
        }
    }

    // Update languages in the freelancer profile
    fun updateLanguages(id: Int, newLanguages: List<String>) = viewModelScope.launch {
        val freelancer = repository.getFreelancerProfileById(id).value
        freelancer?.let {
            val updatedProfile = it.copy(languages = newLanguages)
            repository.update(updatedProfile)
        }
    }

    // Update social links in the freelancer profile
    fun updateSocialLinks(id: Int, newLinks: List<String>) = viewModelScope.launch {
        val freelancer = repository.getFreelancerProfileById(id).value
        freelancer?.let {
            val updatedProfile = it.copy(socialLinks = newLinks)
            repository.update(updatedProfile)
        }
    }

    // Update portfolio links in the freelancer profile
    fun updatePortfolioLinks(id: Int, newLinks: List<String>) = viewModelScope.launch {
        val freelancer = repository.getFreelancerProfileById(id).value
        freelancer?.let {
            val updatedProfile = it.copy(portfolioLinks = newLinks)
            repository.update(updatedProfile)
        }
    }

    // Toggle the 'isFavorite' status of a freelancer profile
    fun toggleFavorite(id: Int) = viewModelScope.launch {
        val freelancer = repository.getFreelancerProfileById(id).value
        freelancer?.let {
            val updatedProfile = it.copy(isFavorite = !it.isFavorite)
            repository.update(updatedProfile)
        }
    }

    // Save a new freelancer profile (from the form)
    fun saveProfile(
        name: String,
        email: String,
        phone: String,
        roleTitle: String,
        summary: String,
        hourlyRate: Double,
        availability: Boolean,
        skills: List<String>,
        certifications: List<Certification>,
        languages: List<String>,
        socialLinks: List<String>,
        portfolioLinks: List<String>
    ) {
        val newProfile = FreelancerProfile(
            name = name,
            email = email,
            phone = phone,
            roleTitle = roleTitle,
            summary = summary,
            hourlyRate = hourlyRate,
            availability = availability,
            skills = skills,
            certifications = certifications,
            experience = emptyList(), // Assuming empty experience and education lists for now
            education = emptyList(),
            projects = emptyList(),
            testimonials = emptyList(),
            languages = languages,
            location = "Unknown", // Default location value
            socialLinks = socialLinks,
            portfolioLinks = portfolioLinks,
            isFavorite = false
        )

        viewModelScope.launch {
            insert(newProfile) // Save the profile to the repository
        }
    }
}
