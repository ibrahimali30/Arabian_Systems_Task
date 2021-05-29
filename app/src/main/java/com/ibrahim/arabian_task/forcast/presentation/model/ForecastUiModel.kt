package com.ibrahim.arabian_task.forcast.presentation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastUiModel(
    @PrimaryKey
    val id: Int = 0 ,
    val main: String = "",
    val weather: String = "",
    val date: String = "",
    val temp: Double = 0.0,
    val feels_like: Double = 0.0,
    val temp_max: Double = 0.0,
    val temp_min: Double = 0.0,
    val dt: Long =0,
)