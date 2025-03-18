package com.example.freelancerportfolio.data

import androidx.lifecycle.LiveData

class FreelancerRepository(private val freelancerDao: FreelancerDao) {

    // Renamed to match the updated DAO methods and reflect a single freelancer's profiles
    val allFreelancerProfiles: LiveData<List<FreelancerProfile>> = freelancerDao.getAllFreelancerProfiles()
    val favoriteFreelancerProfiles: LiveData<List<FreelancerProfile>> = freelancerDao.getFavoriteFreelancerProfiles()

    suspend fun insert(profile: FreelancerProfile) {
        freelancerDao.insert(profile)
    }

    suspend fun update(profile: FreelancerProfile) {
        freelancerDao.update(profile)
    }

    suspend fun delete(profile: FreelancerProfile) {
        freelancerDao.delete(profile)
    }

    // Renamed to match the updated method in DAO
    fun getFreelancerProfileById(id: Int): LiveData<FreelancerProfile> {
        return freelancerDao.getFreelancerProfileById(id)
    }
}
