package com.example.freelancerportfolio.data

import androidx.lifecycle.LiveData

class FreelancerRepository(private val freelancerDao: FreelancerDao) {

    val allFreelancers: LiveData<List<FreelancerProfile>> = freelancerDao.getAllFreelancers()
    val favoriteFreelancers: LiveData<List<FreelancerProfile>> = freelancerDao.getFavoriteFreelancers()

    suspend fun insert(profile: FreelancerProfile) {
        freelancerDao.insert(profile)
    }

    suspend fun update(profile: FreelancerProfile) {
        freelancerDao.update(profile)
    }

    suspend fun delete(profile: FreelancerProfile) {
        freelancerDao.delete(profile)
    }

    fun getFreelancerById(id: Int): LiveData<FreelancerProfile> {
        return freelancerDao.getFreelancerById(id)
    }

    fun searchProfiles(query: String): LiveData<List<FreelancerProfile>> {
        return freelancerDao.searchByRoleOrSkill(query)
    }
}
