package com.ibrahim.arabian_task.forcast.data.source.remote

import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import io.reactivex.Single
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import javax.inject.Inject

class ForecastRemoteDataSource @Inject constructor(
    private val forecastApiService: ForecastApiService
) {

     fun fetchForecast(params: ForecastParams): Single<CityWeatherResponse> {
       return forecastApiService.getForecast()
     }

}