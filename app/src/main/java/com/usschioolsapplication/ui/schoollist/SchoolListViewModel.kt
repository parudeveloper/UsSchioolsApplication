package com.usschioolsapplication.ui.schoollist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usschioolsapplication.data.local.SchoolDetailsRepositoryInterface
import com.usschioolsapplication.domine.HighSchoolList
import com.usschioolsapplication.domine.HighSchoolListItem
import com.usschioolsapplication.repository.HighSchoolRepository
import com.usschioolsapplication.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(private val repository: HighSchoolRepository,private val schoolDetailsRepository: SchoolDetailsRepositoryInterface) :
    ViewModel() {

    private val _uiState: MutableStateFlow<UIState<ArrayList<HighSchoolListItem>>> =
        MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<ArrayList<HighSchoolListItem>>> get() = _uiState.asStateFlow()

    fun getHighSchoolsFromNetwork() {
        viewModelScope.launch {
            repository.fetchHighSchoolsFromNetwork().collect { state ->
                _uiState.value = state
            }
        }
    }


    fun getHighSchoolsFromDB() {
        viewModelScope.launch {
            repository.getHighSchoolsFromDb().collect { state ->
                Log.d("LocalDB",  _uiState.value.toString())
                _uiState.value = state
            }
        }
    }


}