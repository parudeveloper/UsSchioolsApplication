package com.usschioolsapplication.ui.schooldetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.usschioolsapplication.databinding.ActivitySchoolDetailsBinding
import com.usschioolsapplication.domine.HighSchoolListItem
import com.usschioolsapplication.domine.HighSchoolScoresItem
import com.usschioolsapplication.ui.UIState
import com.usschioolsapplication.ui.schoollist.SchoolListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySchoolDetailsBinding
    lateinit var schoolDetailsViewModel: SchoolDetailsViewModel
    lateinit var highSchoolListItem: ArrayList<HighSchoolScoresItem>
    lateinit var dbn: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitle("School Details")


        val highSchoolListData = intent.getParcelableExtra<HighSchoolListItem>("highSchoolListData")
        highSchoolListData?.let {
            dbn = highSchoolListData.dbn
            Log.d("SchoolDetailsActivity", dbn)
            Log.d("SchoolDetailsActivity", highSchoolListData.toString())

            binding.tvSchoolWebsite.text = buildString {
                append("Website: ")
                append(it.website?:"Not Available")
            }


        }


        schoolDetailsViewModel = ViewModelProvider(this).get(SchoolDetailsViewModel::class.java)
        with(binding) {
            lifecycleScope.launch {
                schoolDetailsViewModel.uiState.collectLatest() {
                    when (it) {
                        UIState.Loading -> {
                            Log.d("SchoolDetailsActivity", "Data is loading")
                        }

                        is UIState.Success -> {
                            Log.d("SchoolDetailsActivity", it.data.toString())
                            Log.d("SchoolDetailsActivity", it.data.body().toString())
                            Log.d("SchoolDetailsActivity", it.data.body()?.size.toString())
                            it.data.body()?.forEach {
                                tvSchoolName.text = it.schoolName
                                tvSATTestTakers.text = buildString {
                                    append("Total SAT Test Takers: ")
                                    append(it.numOfSatTestTakers ?: "Not Available")
                                }
                                tvReadingAvg.text = buildString {
                                    append("Reading Avg: ")
                                    append(it.satCriticalReadingAvgScore ?: "Not Available")
                                }
                                tvMathAvg.text = buildString {
                                    append("Math Avg: ")

                                    append(it.satMathAvgScore ?: "Not Available")
                                }
                                tvWritingAvg.text = buildString {
                                    append("Writing Avg: ")
                                    append(it.satWritingAvgScore ?: "Not Available")
                                }

                            }


                        }

                        is UIState.Error -> {
                            Log.d(
                                "SchoolDetailsActivity",
                                "Something went wrong please try again later"
                            )

                        }
                    }

                }
            }
        }
        schoolDetailsViewModel.getHighSchoolScoresFromNetwork(dbn)

    }
}