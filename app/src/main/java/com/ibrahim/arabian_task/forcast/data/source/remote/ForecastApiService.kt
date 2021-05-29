package com.ibrahim.arabian_task.forcast.data.source.remote

import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApiService {

    @GET("")
    fun getForecast(

    ): Single<CityWeatherResponse>
}