package com.example.freelancerportfolio.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FreelancerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(freelancer: FreelancerProfile)

    @Update
    suspend fun update(freelancer: FreelancerProfile)

    @Delete
    suspend fun delete(freelancer: FreelancerProfile)

    @Query("SELECT * FROM freelancer_profile ORDER BY roleTitle ASC")
    fun getAllFreelancerProfiles(): LiveData<List<FreelancerProfile>>

    @Query("SELECT * FROM freelancer_profile WHERE id = :id")
    fun getFreelancerProfileById(id: Int): LiveData<FreelancerProfile>

    @Query("SELECT * FROM freelancer_profile WHERE isFavorite = 1")
    fun getFavoriteFreelancerProfiles(): LiveData<List<FreelancerProfile>>
}

