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

    @Query("SELECT * FROM freelancer_profile ORDER BY name ASC")
    fun getAllFreelancers(): LiveData<List<FreelancerProfile>>

    @Query("SELECT * FROM freelancer_profile WHERE id = :id")
    fun getFreelancerById(id: Int): LiveData<FreelancerProfile>

    @Query("SELECT * FROM freelancer_profile WHERE isFavorite = 1")
    fun getFavoriteFreelancers(): LiveData<List<FreelancerProfile>>

    // Search by skill or roleTitle
    @Query("SELECT * FROM freelancer_profile WHERE roleTitle LIKE '%' || :query || '%' OR skills LIKE '%' || :query || '%'")
    fun searchByRoleOrSkill(query: String): LiveData<List<FreelancerProfile>>
}
