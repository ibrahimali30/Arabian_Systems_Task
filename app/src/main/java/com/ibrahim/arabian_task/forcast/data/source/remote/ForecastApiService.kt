package com.ibrahim.arabian_task.forcast.data.source.remote

import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApiService {

    @GET("find")
    fun getForecast(
        @Query("q") cityname : String = "cairo",
        @Query("units") units : String = "link%2C%20accurate"
    ): Single<CityWeatherResponse>
}