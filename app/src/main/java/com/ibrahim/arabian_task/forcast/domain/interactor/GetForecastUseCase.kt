package com.ibrahim.arabian_task.forcast.domain.interactor

import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.domain.repsitory.ForecastRepository
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import io.reactivex.Single
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val forecastRepository: ForecastRepository) {

    fun fetchForecast(params: ForecastParams): Single<List<ForecastUiModel>> {
        return forecastRepository.fetchForecast(params)
    }

}