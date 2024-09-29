package com.usschioolsapplication.repository

import com.usschioolsapplication.data.local.SchoolDetailsDao
import com.usschioolsapplication.data.network.ApiService
import com.usschioolsapplication.domine.HighSchoolList
import com.usschioolsapplication.domine.HighSchoolScores
import com.usschioolsapplication.ui.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class HighSchoolsScoresRepository @Inject constructor(
    private val apiService: ApiService, private val userDao: SchoolDetailsDao
) {

    suspend fun fetchHighSchoolsScores(dbn:String): Flow<UIState<Response<HighSchoolScores>>> = flow {
        emit(UIState.Loading)
        try {
            val data = apiService.getAllSATScores(dbn)
            emit(UIState.Success(data))
        } catch (e: Exception) {
            emit(UIState.Error(e))
        }
    }
}