package com.ibrahim.arabian_task.forcast.presentation.model

import com.ibrahim.arabian_task.extensions.timeStampToFormattedString
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

fun CitySnipetWeatherResponse.mapToUiModel(): List<ForecastUiModel> {
    val daysList = this.list.groupBy {
        it.dt.timeStampToFormattedString()
    }.filter { it.value.isNotEmpty() }
            .map {
                it.value[0]
            }
    return daysList.map { weatherMain ->
        val weather = weatherMain.weather[0]
        val main = weatherMain.main
        ForecastUiModel(
                weather.main,
                weather.description,
               "",
                main.temp,
                main.feels_like,
                main.temp_max,
                main.temp_min,
                weatherMain.wind.speed,
                weatherMain.dt

        )
    }

}
