package com.ibrahim.arabian_task.forcast.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.arabian_task.R
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.arabian_task.forcast.presentation.view.adapter.FiveDaysForecastAdapter
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.FiveDaysForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_forecast_result.*
import kotlinx.android.synthetic.main.layout_error_view.*
import javax.inject.Inject


@AndroidEntryPoint
class ForecastResultFragment(
        val cityName: String
) : Fragment(R.layout.fragment_forecast_result){

    @Inject
    lateinit var viewModel: FiveDaysForecastViewModel
    lateinit var adapter: FiveDaysForecastAdapter

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

    private fun onScreenStateChanged(state: FiveDaysForecastViewModel.ForecastScreenState?) {
        when (state) {
            is FiveDaysForecastViewModel.ForecastScreenState.SuccessAPIResponse -> handleSuccess(state.data)
            is FiveDaysForecastViewModel.ForecastScreenState.ErrorLoadingFromApi -> handleErrorLoadingFromApi(state.error)
            else -> {}
        }

        handleLoadingVisibility(state == FiveDaysForecastViewModel.ForecastScreenState.Loading)
        handleErrorViewsVisibility(state)

    }

    private fun handleErrorViewsVisibility(state: FiveDaysForecastViewModel.ForecastScreenState?) {
        if (state is FiveDaysForecastViewModel.ForecastScreenState.ErrorLoadingFromApi)
            errorViewLayoutFragment.visibility = View.VISIBLE
        else
            errorViewLayoutFragment.visibility = View.GONE

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