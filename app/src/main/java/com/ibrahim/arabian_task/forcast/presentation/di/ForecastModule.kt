package com.ibrahim.arabian_task.forcast.presentation.di

import dagger.Module
import dagger.Provides
import com.ibrahim.arabian_task.forcast.data.repository.ForecastRepositoryImpl
import com.ibrahim.arabian_task.forcast.domain.repsitory.ForecastRepository
import com.ibrahim.arabian_task.forcast.data.source.remote.ForecastApiService
import com.ibrahim.arabian_task.forcast.data.source.local.ForecastDao
import com.ibrahim.code95_task.db.WeatherDatabase
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class ForecastModule {


    @Provides
    fun providesForecastRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository = forecastRepositoryImpl

    @Provides
    fun providesForecastApiService(builder: Retrofit.Builder): ForecastApiService {
        return builder.build().create(ForecastApiService::class.java)
    }


    @Provides
    fun providesForecastDao(WeatherDatabase: WeatherDatabase): ForecastDao = WeatherDatabase.forecastDao()

}