package com.ibrahim.arabian_task.forcast.presentation.model

import com.ibrahim.arabian_task.forcast.data.model.snipet.CitySnipetWeatherResponse
import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import java.text.SimpleDateFormat
import java.util.*


fun CityWeatherResponse.mapToUiModel(): ForecastUiModel {
    // TODO: 5/29/2021 check for lists sizes to avoid out of bounds exception
    val weatherMain = list[0]
    val weather = weatherMain.weather[0]
    val main = weatherMain.main

    val day =""

    weatherMain.apply {
        return ForecastUiModel(
            id,
            weather.main,
            weather.description,
            "",
            main.temp,
            main.feels_like,
            main.temp_max,
            main.temp_min

        )
    }

}

private fun getFormattedDate(day: String): String? {
    var format = SimpleDateFormat("yyyy-MM-dd")
    val newDate: Date = format.parse(day)
    format = SimpleDateFormat("EEE, dd MMM")
    return format.format(newDate)
}


fun CitySnipetWeatherResponse.mapToUiModel(): ForecastUiModel {
    // TODO: 5/29/2021 check for lists sizes to avoid out of bounds exception
    val weatherMain = list[0]
    val weather = weatherMain.weather[0]
    val main = weatherMain.main

    weatherMain.apply {
        return ForecastUiModel(
            0,
            weather.main,
            weather.description,
            "",
            main.temp,
            main.feels_like,
            main.temp_max,
            main.temp_min
        )
    }

}
