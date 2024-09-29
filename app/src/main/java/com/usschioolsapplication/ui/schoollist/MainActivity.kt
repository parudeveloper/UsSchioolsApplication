package com.usschioolsapplication.ui.schoollist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.usschioolsapplication.databinding.ActivityMainBinding
import com.usschioolsapplication.domine.HighSchoolListItem
import com.usschioolsapplication.ui.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: SchoolListViewModel
    lateinit var listOfSchoolData: ArrayList<HighSchoolListItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.schoolListToolbar.title = "NYC High Schools"
        listOfSchoolData = ArrayList<HighSchoolListItem>()
        viewModel = ViewModelProvider(this).get(SchoolListViewModel::class.java)
        with(binding) {
            lifecycleScope.launch {
                viewModel.uiState.collectLatest() {
                    when (it) {
                        UIState.Loading -> {
                            Log.d("MainActivity", "Data is loading")
                        }

                        is UIState.Success -> {
                            progressBar.visibility = View.GONE

                            Log.d("MainActivity", it.data.toString())
                            Log.d("MainActivity1", it.data.toString())
                            it.data.let {
                                listOfSchoolData.addAll(it)
                                addDataToAdapter(listOfSchoolData)
                            }
                        }

                        is UIState.Error -> {
                            progressBar.visibility = View.GONE

                            Log.d("MainActivity", "Something went wrong please try again later")

                        }
                    }

                }
            }
        }
        loadSchools()

    }

    private fun addDataToAdapter(listOfSchoolData: ArrayList<HighSchoolListItem>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = SchoolListAdapter(listOfSchoolData)

    }

    private fun loadSchools(showSavedOnly: Boolean = false, forceNetwork: Boolean = false) {
        println("loadSchools: $showSavedOnly")
        lifecycleScope.launch {
            if (showSavedOnly) {
                /* viewModel.uiState.collectLatest() {

                     when (it) {
                         UIState.Loading -> {
                             Log.d("MainActivity", "Data is loading")
                         }

                         is UIState.Success -> {
                             binding.progressBar.visibility = View.GONE
                             Log.d("MainActivity", it.data.toString())
                             Log.d("MainActivity1", it.data.toString())
                             it.data.let {
                                 listOfSchoolData.addAll(it)
                                 addDataToAdapter(listOfSchoolData)
                             }
                         }

                         is UIState.Error -> {
                             binding.progressBar.visibility = View.GONE

                             Log.d("MainActivity", "Something went wrong please try again later")

                         }
                     }
                 }*/
                viewModel.getHighSchoolsFromDB()
            } else {
                viewModel.getHighSchoolsFromNetwork()
            }

        }
    }
}