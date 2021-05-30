package com.ibrahim.arabian_task.db

import androidx.room.*
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.arabian_task.forcast.data.source.local.ForecastDao

@Database(
    entities = [
        ForecastUiModel::class
    ],
    version = 2 , exportSchema = false
)
//@TypeConverters(DataConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao


}

