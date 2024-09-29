package com.usschioolsapplication.repository

import androidx.lifecycle.viewModelScope
import com.usschioolsapplication.data.local.SchoolDetailsEntity
import com.usschioolsapplication.data.local.SchoolDetailsRepositoryInterface
import com.usschioolsapplication.data.network.ApiService
import com.usschioolsapplication.domine.HighSchoolListItem
import com.usschioolsapplication.ui.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HighSchoolRepository @Inject constructor(
    private val apiService: ApiService,
    private val schoolDetailsRepository: SchoolDetailsRepositoryInterface
) {
    val schoolDetailsEntity = ArrayList<SchoolDetailsEntity>()
    private var _savedSchoolsDbn: List<String> = emptyList()
    private var fetchedData: ArrayList<HighSchoolListItem>? = null



    suspend fun fetchHighSchoolsFromNetwork(): Flow<UIState<ArrayList<HighSchoolListItem>>> = flow {
        emit(UIState.Loading)
        try {
            _savedSchoolsDbn=schoolDetailsRepository.getSavedSchools()
            if (fetchedData == null) {
                val response = apiService.getAllHighSchools() // avoid fetching data again if already fetched
                fetchedData = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            }
            fetchedData?.let {
                it.forEach { school ->
                    if (_savedSchoolsDbn.contains(school.dbn)) {
                        school.isSaved = true
                    }else{
                        school.isSaved = false
                    }
                }
            }

            fun getHighSchoolList(): ArrayList<HighSchoolListItem>? {
                return fetchedData
            }
            emit(UIState.Success(getHighSchoolList()?: ArrayList()))
        } catch (e: Exception) {
            emit(UIState.Error(e))
        }
    }
    suspend fun getHighSchoolsFromDb() : Flow<UIState<ArrayList<HighSchoolListItem>>> = flow {
        emit(UIState.Loading)

        println("getHighSchoolsFromDb")
            try {
                val  savedSchools = schoolDetailsRepository.getAllSchoolDetails()

                val highSchoolItemList:ArrayList<HighSchoolListItem> = ArrayList()
                savedSchools.forEach {
                    highSchoolItemList.add(HighSchoolListItem.fromSchoolDetailsEntity(it))
                }
                emit(UIState.Success(highSchoolItemList))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(UIState.Error(e))



            }
    }

}