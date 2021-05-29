package com.ibrahim.arabian_task.forcast.presentation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastUiModel(
    @PrimaryKey
    val id: Int = 0
)