package com.ibrahim.arabian_task.forcast.data.repository

import io.reactivex.Single
import com.ibrahim.arabian_task.forcast.data.source.remote.ForecastRemoteDataSource
import com.ibrahim.arabian_task.forcast.domain.repsitory.ForecastRepository
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.arabian_task.forcast.data.source.local.ForecastLocalDataSource
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.presentation.model.mapToUiModel
import javax.inject.Inject



class ForecastRepositoryImpl @Inject constructor(
    private val forecastRemoteDataSource: ForecastRemoteDataSource,
    private val forecastLocalDataSource: ForecastLocalDataSource
) : ForecastRepository {

    override fun fetchForecast(params: ForecastParams): Single<ForecastUiModel> {
        return forecastRemoteDataSource.fetchForecast(params)
                .map { cityWeatherResponse ->
                    cityWeatherResponse.mapToUiModel()
                }
    }

    override fun getForecastFromLocalDB(cityName: String): Single<ForecastUiModel> {
        return forecastLocalDataSource.getForecastByCityName(cityName)
    }

    override fun insertForecastIntoLocalDB(forecastUiModel: ForecastUiModel) {
        forecastLocalDataSource.insertForecastUiModel(forecastUiModel)
    }


}
