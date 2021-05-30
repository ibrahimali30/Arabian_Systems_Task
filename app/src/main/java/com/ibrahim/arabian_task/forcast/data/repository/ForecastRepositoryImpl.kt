package com.ibrahim.arabian_task.forcast.data.repository

import com.ibrahim.arabian_task.forcast.data.source.local.ForecastLocalDataSource
import com.ibrahim.arabian_task.forcast.data.source.remote.ForecastRemoteDataSource
import com.ibrahim.arabian_task.forcast.domain.entity.EndPoint
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.domain.repsitory.ForecastRepository
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.arabian_task.forcast.presentation.model.mapToUiModel
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


class ForecastRepositoryImpl @Inject constructor(
    private val forecastRemoteDataSource: ForecastRemoteDataSource,
    private val forecastLocalDataSource: ForecastLocalDataSource
) : ForecastRepository {

    override fun fetchForecast(params: ForecastParams): Single<List<ForecastUiModel>> {
        return if (params.endPoint == EndPoint.find){
            forecastRemoteDataSource.fetchForecast(params)
                    .map { cityWeatherResponse ->
                        cityWeatherResponse.mapToUiModel()
                    }
        }else{
            forecastRemoteDataSource.fetchForecastSnipet(params)
                    .map { citySnipetWeatherResponse ->
                        citySnipetWeatherResponse.mapToUiModel()
                    }
        }
    }

    override fun getForecastFromLocalDB(): Flowable<List<ForecastUiModel>> {
        return forecastLocalDataSource.getSavedForecasts()
    }

    override fun insertOrDelete(forecastUiModel: ForecastUiModel) {
        forecastLocalDataSource.insertOrDelete(forecastUiModel)
    }


}
