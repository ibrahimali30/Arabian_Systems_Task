package com.ibrahim.arabian_task.forcast.data.source.local

import androidx.room.*
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import io.reactivex.Flowable


@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecastUiModel(ForecastUiModel: ForecastUiModel):Long

    @Delete()
    fun deleteForecastUiModel(ForecastUiModel: ForecastUiModel)

    @Query("select * from ForecastUiModel")
    fun getForecastByCityName(): Flowable<List<ForecastUiModel>>


}