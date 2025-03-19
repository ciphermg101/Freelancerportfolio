package com.example.freelancerportfolio.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

class FreelancerRepository(private val freelancerDao: FreelancerDao) {

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

    suspend fun getFreelancerProfileByIdSync(id: Int): FreelancerProfile? {
        return freelancerDao.getFreelancerProfileByIdSync(id)
    }

    fun getFreelancerProfileById(id: Int): LiveData<FreelancerProfile?> = liveData {
        emit(freelancerDao.getFreelancerProfileByIdSync(id))
    }
}
