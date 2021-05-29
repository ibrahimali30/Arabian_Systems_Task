package com.ibrahim.arabian_task.forcast.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel


@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecastUiModel(ForecastUiModel: ForecastUiModel):Long

    @Query("select * from ForecastUiModel where :cityName like :cityName limit 1")
    fun getForecastByCityName(cityName: String): ForecastUiModel


}