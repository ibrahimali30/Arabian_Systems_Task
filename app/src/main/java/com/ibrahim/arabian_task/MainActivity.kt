package com.ibrahim.arabian_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.presentation.adapter.Adapter
import com.ibrahim.arabian_task.forcast.presentation.adapter.ForecastAdapter
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.arabian_task.forcast.presentation.view.viewmodel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error_view.*
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG = "Looog"

    lateinit var adapter: ForecastAdapter

    @Inject
    lateinit var viewModel: ForecastViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeScreenState()
        initSearchView()
        initRecyclerView()
        viewModel.getForecast("Cairo")
    }

    private fun initRecyclerView() {
        adapter = Adapter(::onSaveButtonClicked)

        rvForecast.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        rvForecast.adapter = adapter
    }

    private fun onSaveButtonClicked(forecastUiModel: ForecastUiModel) {
       viewModel.insertForecastIntoLocalDB(forecastUiModel)
    }

    private fun initSearchView() {

//        searchView.isActivated = true
//        searchView.setIconifiedByDefault(false)
//        searchView.isIconified = false

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

    private fun onScreenStateChanged(state: ForecastViewModel.ForecastScreenState?) {
        Log.d(TAG, "onScreenStateChanged: ${state.toString()}")

        when (state) {
            is ForecastViewModel.ForecastScreenState.SuccessAPIResponse -> handleSuccess(state.data)
            is ForecastViewModel.ForecastScreenState.ErrorLoadingFromApi -> handleErrorLoadingFromApi(state.error)
//            is ForecastViewModel.ForecastScreenState.SuccessLocalDB -> handleSuccess(state.data)
//            is ForecastViewModel.ForecastScreenState.ErrorLoadingFromLocalDB ->
//                handleErrorLoadingFromLocalDB(state.error , state.retry)
            else -> {}
        }

        handleLoadingVisibility(state == ForecastViewModel.ForecastScreenState.Loading)
        handleErrorViewsVisibility(state)

    }

    private fun handleErrorViewsVisibility(state: ForecastViewModel.ForecastScreenState?) {
        if (state is ForecastViewModel.ForecastScreenState.ErrorLoadingFromApi)
            errorViewLayout.visibility = View.VISIBLE
        else
            errorViewLayout.visibility = View.GONE

    }


    private fun handleErrorLoadingFromLocalDB(error: Throwable?, retry: () -> Unit) {
        btRetry.setOnClickListener {
//            retry.invoke()
            viewModel.getForecast(searchView.query.toString())
        }
    }

    private fun handleErrorLoadingFromApi(error: Throwable) {
        Log.d(TAG, "handleErrorLoadingFromApi: ")
    }

    private fun handleSuccess(data: ForecastUiModel) {

        adapter.setList(data)
        adapter.notifyDataSetChanged()
    }

    private fun handleLoadingVisibility(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


}