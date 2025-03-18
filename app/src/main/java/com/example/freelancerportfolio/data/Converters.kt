package com.example.freelancerportfolio.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromJobExperienceList(value: List<JobExperience>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toJobExperienceList(value: String): List<JobExperience> {
        return gson.fromJson(value, object : TypeToken<List<JobExperience>>() {}.type)
    }

    @TypeConverter
    fun fromEducationList(value: List<Education>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toEducationList(value: String): List<Education> {
        return gson.fromJson(value, object : TypeToken<List<Education>>() {}.type)
    }

    @TypeConverter
    fun fromCertificationList(value: List<Certification>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCertificationList(value: String): List<Certification> {
        return gson.fromJson(value, object : TypeToken<List<Certification>>() {}.type)
    }

    @TypeConverter
    fun fromProjectList(value: List<Project>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProjectList(value: String): List<Project> {
        return gson.fromJson(value, object : TypeToken<List<Project>>() {}.type)
    }

    @TypeConverter
    fun fromTestimonialList(value: List<Testimonial>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTestimonialList(value: String): List<Testimonial> {
        return gson.fromJson(value, object : TypeToken<List<Testimonial>>() {}.type)
    }
}
