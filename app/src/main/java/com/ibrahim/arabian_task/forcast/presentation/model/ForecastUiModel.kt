package com.ibrahim.arabian_task.forcast.presentation.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ForecastUiModel(

    var main: String = "",
    var description: String = "",
    @PrimaryKey
    var name: String = "",
    var temp: Double = 0.0,
    var feels_like: Double = 0.0,
    var temp_max: Double = 0.0,
    var temp_min: Double = 0.0,
    var windSpeed: Double = 0.0,
    var dt: Long =0,
    @Ignore
    var isFavourite: Boolean = false
)