package com.ibrahim.arabian_task.forcast.data.model.weather

data class CityWeatherResponse(
    val cod: String,
    val count: Int,
    val list: List<CityWeather>,
    val message: String
)

