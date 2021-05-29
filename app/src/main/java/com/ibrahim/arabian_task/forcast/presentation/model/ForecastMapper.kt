package com.ibrahim.arabian_task.forcast.presentation.model

import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import java.util.ArrayList


fun CityWeatherResponse.mapToUiModel(): ForecastUiModel {
    // TODO: 5/29/2021 check for lists sizes to avoid out of bounce exception
    val weatherMain = list[0]
    val weather = list[0].weather[0]
    val main = weatherMain.main
    
    weatherMain.apply {
        return ForecastUiModel(
            id,
            weather.main,
            weather.description,
            main.temp,
            main.feels_like,
            main.temp_max,
            main.temp_min

        )
    }

}

//private fun List<WeatherSnipet>.mapToWeatherSnippetUiModel(): ArrayList<WeatherSnipetUiModel> {
//    val list = map {
//        val icon = if (it.weather.isNotEmpty()) it.weather[0].icon else ""
//            WeatherSnipetUiModel(
//                it.dt,
//                it.dt_txt,
//                it.main.temp,
//                it.pop,
//                it.visibility,
//                icon
//            )
//    }
//    return ArrayList(list)
//}
