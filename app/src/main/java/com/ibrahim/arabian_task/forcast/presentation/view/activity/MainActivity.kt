package com.ibrahim.arabian_task.forcast.presentation.view.activity

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import com.ibrahim.arabian_task.R
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.arabian_task.forcast.presentation.view.adapter.ForecastAdapter
import com.ibrahim.arabian_task.forcast.presentation.view.fragment.ForecastResultFragment
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.CityForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error_view.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG = "Looog"

    lateinit var adapter: ForecastAdapter

    @Inject
    lateinit var viewModel: CityForecastViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeScreenState()
        initSearchView()
        initRecyclerView()
        viewModel.getSavedForecast()
    }

    private fun initRecyclerView() {
        adapter = ForecastAdapter(::onSaveButtonClicked, ::onAForecastItemClicked)

        rvForecast.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        rvForecast.adapter = adapter
    }

    private fun onAForecastItemClicked(forecastUiModel: ForecastUiModel) {
        navigateToForecastFragment(forecastUiModel)
    }

    private fun onSaveButtonClicked(forecastUiModel: ForecastUiModel) {
        viewModel.insertOrDelete(forecastUiModel)
    }

    private fun navigateToForecastFragment(forecastUiModel: ForecastUiModel) {
        val forecastDetailsFragment = ForecastResultFragment(forecastUiModel.name)
        forecastDetailsFragment.enterTransition = Slide(Gravity.END)

        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, forecastDetailsFragment)
                .addToBackStack(forecastDetailsFragment::class.java.name)
                .commit()
    }

    private fun initSearchView() {

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            Log.d("TAG", "initSearchView: $hasFocus")
            viewModel.isInSearchMode = hasFocus
            if (!hasFocus){
                viewModel.getSavedForecast()
                searchView.setQuery("",false)
            }

        }
        searchView.setOnCloseListener {
            searchView.clearFocus()
            return@setOnCloseListener false
        }

        searchView.isActivated = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.clear()
                viewModel.getForecast(searchView.query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {return false}
        })

    }

    private fun observeScreenState() {
        viewModel.screenState.observe(this , Observer {
            onScreenStateChanged(it)
        })
    }

    private fun onScreenStateChanged(state: CityForecastViewModel.ForecastScreenState?) {
        Log.d(TAG, "onScreenStateChanged: ${state.toString()}")

        when (state) {
            is CityForecastViewModel.ForecastScreenState.SuccessAPIResponse -> handleSuccess(state.data)
            is CityForecastViewModel.ForecastScreenState.SuccessLocalData -> handleSuccess(state.data)
            is CityForecastViewModel.ForecastScreenState.ErrorLoadingFromApi -> handleErrorLoadingFromApi(state.error)
            else -> {}
        }

        handleLoadingVisibility(state == CityForecastViewModel.ForecastScreenState.Loading)
        handleErrorViewsVisibility(state)

    }

    private fun handleErrorViewsVisibility(state: CityForecastViewModel.ForecastScreenState?) {
        if (state is CityForecastViewModel.ForecastScreenState.ErrorLoadingFromApi)
            errorViewLayout.visibility = View.VISIBLE
        else
            errorViewLayout.visibility = View.GONE

    }

    private fun handleErrorLoadingFromApi(error: Throwable) {
        btRetry.setOnClickListener {
            viewModel.getForecast(searchView.query.toString())
        }
    }

    private fun handleSuccess(data: List<ForecastUiModel>) {
        if (data.isEmpty() && !viewModel.isInSearchMode){
            tvNoSoredData.visibility = View.VISIBLE
        }else{
            tvNoSoredData.visibility = View.INVISIBLE
        }
        adapter.setList(data)
    }

    private fun handleLoadingVisibility(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


}