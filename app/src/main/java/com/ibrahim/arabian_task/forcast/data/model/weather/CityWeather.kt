package com.ibrahim.arabian_task.forcast.data.model.weather

data class CityWeather(
    val clouds: Clouds,
    val coord: Coord,
    val dt: Long,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Any,
    val snow: Any,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)