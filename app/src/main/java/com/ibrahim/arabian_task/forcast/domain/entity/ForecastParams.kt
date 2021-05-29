package com.ibrahim.arabian_task.forcast.domain.entity

data class ForecastParams(
    var cityName: String? = null,
    var lat: Double? = null,
    var lon: Double? = null,
)