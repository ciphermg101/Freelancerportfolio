package com.example.freelancerportfolio.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FreelancerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FreelancerRepository
    val allFreelancers: LiveData<List<FreelancerProfile>>
    val favoriteFreelancers: LiveData<List<FreelancerProfile>>

    init {
        val dao = FreelancerDatabase.getDatabase(application).freelancerDao()
        repository = FreelancerRepository(dao)
        allFreelancers = repository.allFreelancers
        favoriteFreelancers = repository.favoriteFreelancers
    }

    fun insert(profile: FreelancerProfile) = viewModelScope.launch {
        repository.insert(profile)
    }

    fun update(profile: FreelancerProfile) = viewModelScope.launch {
        repository.update(profile)
    }

    fun delete(profile: FreelancerProfile) = viewModelScope.launch {
        repository.delete(profile)
    }

    fun getFreelancerById(id: Int): LiveData<FreelancerProfile> {
        return repository.getFreelancerById(id)
    }

    fun searchProfiles(query: String): LiveData<List<FreelancerProfile>> {
        return repository.searchProfiles(query)
    }
}
