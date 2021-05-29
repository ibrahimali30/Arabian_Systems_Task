package com.ibrahim.arabian_task.forcast.data.source.local

import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import io.reactivex.Single
import javax.inject.Inject


class ForecastLocalDataSource @Inject constructor(
    private val forecastDao: ForecastDao
) {

    fun getForecastByCityName(cityName: String): Single<ForecastUiModel> {
        return Single.fromCallable {
            forecastDao.getForecastByCityName(cityName)
        }
    }

    fun insertForecastUiModel(forecastUiModel: ForecastUiModel) {
         forecastDao.insertForecastUiModel(forecastUiModel)
    }

}