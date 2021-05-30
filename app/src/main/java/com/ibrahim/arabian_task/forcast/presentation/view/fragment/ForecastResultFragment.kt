package com.ibrahim.arabian_task.forcast.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.ForecastRemoteViewModel
import com.ibrahim.arabian_task.R
import com.ibrahim.arabian_task.forcast.domain.entity.EndPoint
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.arabian_task.forcast.presentation.view.adapter.FiveDaysForecastAdapter
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_forecast_result.*
import kotlinx.android.synthetic.main.layout_error_view.*
import javax.inject.Inject


@AndroidEntryPoint
class ForecastResultFragment(
        val cityName: String
) : Fragment(R.layout.fragment_forecast_result){

    @Inject
    lateinit var viewModel: ForecastRemoteViewModel
    lateinit var adapter: FiveDaysForecastAdapter


    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeScreenState()
        initRecyclerView()

        tvTitle.text = cityName
        viewModel.getForecast(cityName)

    }


    private fun initRecyclerView() {
        adapter = FiveDaysForecastAdapter()
        rvForecast.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvForecast.adapter = adapter
    }


    private fun observeScreenState() {
        viewModel.screenState.observe(viewLifecycleOwner , Observer {
            onScreenStateChanged(it)
        })
    }

    private fun onScreenStateChanged(state: ForecastRemoteViewModel.ForecastScreenState?) {
        when (state) {
            is ForecastRemoteViewModel.ForecastScreenState.SuccessAPIResponse -> handleSuccess(state.data)
            is ForecastRemoteViewModel.ForecastScreenState.ErrorLoadingFromApi -> handleErrorLoadingFromApi(state.error)
            else -> {}
        }

        handleLoadingVisibility(state == ForecastRemoteViewModel.ForecastScreenState.Loading)
        handleErrorViewsVisibility(state)

    }

    private fun handleErrorViewsVisibility(state: ForecastRemoteViewModel.ForecastScreenState?) {
        if (state is ForecastRemoteViewModel.ForecastScreenState.ErrorLoadingFromApi)
            errorViewLayout.visibility = View.VISIBLE
        else
            errorViewLayout.visibility = View.GONE

    }

    private fun handleErrorLoadingFromApi(error: Throwable) {
        btRetry.setOnClickListener {
            viewModel.getForecast(ForecastParams(cityName,5))
        }
    }

    private fun handleSuccess(data: List<ForecastUiModel>) {
        adapter.setList(data)
        adapter.notifyDataSetChanged()
    }

    private fun handleLoadingVisibility(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }




}