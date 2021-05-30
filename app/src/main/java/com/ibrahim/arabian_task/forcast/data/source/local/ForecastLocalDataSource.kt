package com.ibrahim.arabian_task.forcast.data.source.local

import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ForecastLocalDataSource @Inject constructor(
    private val forecastDao: ForecastDao
) {

    fun getForecastByCityName(): Flowable<List<ForecastUiModel>> {
        return forecastDao.getSavedForecasts()
    }

    fun insertOrDelete(forecastUiModel: ForecastUiModel) {
        Single.fromCallable {
            if (forecastUiModel.isFavourite){
                forecastDao.deleteForecastUiModel(forecastUiModel)
            }else{
                forecastUiModel.isFavourite = true
                forecastDao.insertForecastUiModel(forecastUiModel)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}