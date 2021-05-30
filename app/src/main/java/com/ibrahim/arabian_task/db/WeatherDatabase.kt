package com.ibrahim.arabian_task.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ibrahim.arabian_task.forcast.data.source.local.ForecastDao
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel

@Database(
    entities = [
        ForecastUiModel::class
    ],
    version = 2 , exportSchema = false
)

abstract class WeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao


}

