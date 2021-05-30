package com.ibrahim.arabian_task.forcast.data.source.remote

import com.ibrahim.arabian_task.forcast.data.model.snipet.CitySnipetWeatherResponse
import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import io.reactivex.Single
import javax.inject.Inject

class ForecastRemoteDataSource @Inject constructor(
    private val forecastApiService: ForecastApiService
) {

    fun fetchForecast(params: ForecastParams): Single<CityWeatherResponse> {
        return forecastApiService.getForecast(
                params.endPoint.name,
                params.cityName,
                params.cnt,
                params.units
        )
    }

    fun fetchForecastSnipet(params: ForecastParams): Single<CitySnipetWeatherResponse> {
        return forecastApiService.fetchForecastSnipet(
                params.endPoint.name,
                params.cityName,
                params.cnt,
                params.units
        )
    }

}