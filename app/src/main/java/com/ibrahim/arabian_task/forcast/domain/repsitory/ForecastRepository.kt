package com.ibrahim.arabian_task.forcast.domain.repsitory

import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import io.reactivex.Flowable
import io.reactivex.Single

interface ForecastRepository {

    fun fetchForecast(params: ForecastParams): Single<List<ForecastUiModel>>
    fun getForecastFromLocalDB(): Flowable<List<ForecastUiModel>>
    fun insertOrDelete(forecastUiModel: ForecastUiModel)

}