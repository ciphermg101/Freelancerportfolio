package com.example.freelancerportfolio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FreelancerProfile::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FreelancerDatabase : RoomDatabase() {

    abstract fun freelancerDao(): FreelancerDao

    companion object {
        @Volatile
        private var INSTANCE: FreelancerDatabase? = null

        fun getDatabase(context: Context): FreelancerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FreelancerDatabase::class.java,
                    "freelancer_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
