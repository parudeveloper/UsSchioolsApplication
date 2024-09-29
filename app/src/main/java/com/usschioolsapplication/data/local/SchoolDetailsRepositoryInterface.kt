package com.usschioolsapplication.data.local

interface SchoolDetailsRepositoryInterface {
    suspend fun insertSchoolDetails(schoolDetailsEntity: SchoolDetailsEntity)
    suspend fun deleteSchoolDetails(schoolDetailsEntity: SchoolDetailsEntity)
    suspend fun getSchoolDetails(dbn: String): SchoolDetailsEntity?
    suspend fun getSavedSchools(): List<String>
    suspend fun getAllSchoolDetails(): List<SchoolDetailsEntity>


}