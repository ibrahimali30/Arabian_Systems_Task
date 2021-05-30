package com.ibrahim.arabian_task.forcast.domain.entity

import retrofit2.http.Query

data class ForecastParams(
    val cityName: String,
    val cnt: Int = 1,
    val units: String = "metric",
    val endPoint: EndPoint = EndPoint.find
)

enum class EndPoint{
    forecast,
    find
}