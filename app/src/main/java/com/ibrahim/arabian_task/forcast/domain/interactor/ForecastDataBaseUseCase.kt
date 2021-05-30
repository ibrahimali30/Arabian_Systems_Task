package com.ibrahim.arabian_task.forcast.domain.interactor

import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.domain.repsitory.ForecastRepository
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ForecastDataBaseUseCase @Inject constructor(private val forecastRepository: ForecastRepository) {

    fun getForecastFromLocalDB(): Flowable<List<ForecastUiModel>> {
        return forecastRepository.getForecastFromLocalDB()
    }

    fun insertOrDelete(forecastUiModel: ForecastUiModel) {
        return forecastRepository.insertOrDelete(forecastUiModel)
    }

}