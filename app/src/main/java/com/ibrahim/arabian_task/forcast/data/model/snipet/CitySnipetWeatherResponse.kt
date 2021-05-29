package com.ibrahim.arabian_task.forcast.data.model.snipet


data class CitySnipetWeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherSnipet>,
    val message: Int
)

