package com.ibrahim.arabian_task.forcast.data.source.remote

import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApiService {

    @GET("find")
    fun getForecast(
        @Query("q") cityname : String ,
        @Query("cnt") cnt: Int,
        @Query("units") units: String
    ): Single<CityWeatherResponse>
}