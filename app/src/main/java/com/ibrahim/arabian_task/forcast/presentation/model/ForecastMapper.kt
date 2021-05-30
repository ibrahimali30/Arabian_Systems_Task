package com.ibrahim.arabian_task.forcast.presentation.model

import com.ibrahim.arabian_task.forcast.data.model.snipet.CitySnipetWeatherResponse
import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import java.text.SimpleDateFormat
import java.util.*


fun CityWeatherResponse.mapToUiModel(): List<ForecastUiModel> {


   return list.map { weatherMain ->
        val weather = weatherMain.weather[0]
        val main = weatherMain.main
        ForecastUiModel(
                weather.main,
                weather.description,
                weatherMain.name,
                main.temp,
                main.feels_like,
                main.temp_max,
                main.temp_min,
                weatherMain.wind.speed,
                weatherMain.dt

        )
    }

}

fun CitySnipetWeatherResponse.mapToUiModel(): ForecastUiModel {
    // TODO: 5/29/2021 check for lists sizes to avoid out of bounds exception
    val weatherMain = list[0]
    val weather = weatherMain.weather[0]
    val main = weatherMain.main

    weatherMain.apply {
        return ForecastUiModel(
            weather.main,
            weather.description,
            city.name,
            main.temp,
            main.feels_like,
            main.temp_max,
            main.temp_min,
            wind.speed,
            dt
        )
    }

}
