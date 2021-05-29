package com.ibrahim.code95_task.db

import android.content.Context
import androidx.room.*
import com.ibrahim.arabian_task.db.DataConverter
import com.ibrahim.code95_task.forcast.data.model.search.Search
import com.ibrahim.code95_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.code95_task.forcast.data.source.local.ForecastDao
import com.ibrahim.code95_task.forcast.data.source.local.SearchDao

@Database(
    entities = [
        ForecastUiModel::class,
        Search::class
    ],
    version = 4 , exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao
    abstract fun searchDao(): SearchDao

    companion object {
        var instanceDB: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return if (instanceDB != null) {
                instanceDB!!
            }else{
                instanceDB = Room.databaseBuilder(context, WeatherDatabase::class.java, "WeatherDatabase")
                        .fallbackToDestructiveMigration()
                        .build()
                instanceDB!!
            }

        }
    }
}

