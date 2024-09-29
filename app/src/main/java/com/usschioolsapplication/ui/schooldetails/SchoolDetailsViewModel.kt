package com.usschioolsapplication.ui.schooldetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usschioolsapplication.domine.HighSchoolList
import com.usschioolsapplication.domine.HighSchoolScores
import com.usschioolsapplication.repository.HighSchoolRepository
import com.usschioolsapplication.repository.HighSchoolsScoresRepository
import com.usschioolsapplication.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class SchoolDetailsViewModel @Inject constructor(private val repository: HighSchoolsScoresRepository) :
    ViewModel() {


    private val _uiState: MutableStateFlow<UIState<Response<HighSchoolScores>>> =
        MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<Response<HighSchoolScores>>> get() = _uiState.asStateFlow()

    fun getHighSchoolScoresFromNetwork(dbn :String) {
        viewModelScope.launch {
            repository.fetchHighSchoolsScores(dbn).collect { state ->
                _uiState.value = state
            }
        }
    }

}