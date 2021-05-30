package com.ibrahim.arabian_task.forcast.data.source.remote

import com.ibrahim.arabian_task.forcast.data.model.snipet.CitySnipetWeatherResponse
import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ForecastApiService {

    @GET("{endPoint}")
    fun getForecast(
            @Path("endPoint") endPoint: String,
            @Query("q") cityname : String,
            @Query("cnt") cnt: Int,
            @Query("units") units: String
    ): Single<CityWeatherResponse>

    @GET("{endPoint}")
    fun fetchForecastSnipet(
            @Path("endPoint") endPoint: String,
            @Query("q") cityname : String,
            @Query("cnt") cnt: Int,
            @Query("units") units: String
    ): Single<CitySnipetWeatherResponse>
}