package com.ibrahim.arabian_task.forcast.data.source.remote

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.ibrahim.arabian_task.forcast.domain.entity.EndPoint
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.utils.RxSchedulerRule
import com.ibrahim.arabian_task.utils.TestUtils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class ForecastRemoteDataSourceTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
    lateinit var wordsLocalDataSource: ForecastRemoteDataSource
    @MockK
    private lateinit var wordsApiService: ForecastApiService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        wordsLocalDataSource =  ForecastRemoteDataSource(wordsApiService)
    }

    @Test
    fun fetchForecast() {
        val params = ForecastParams("Cairo",1,"metric",EndPoint.find)
        val cityWeatherResponse = TestUtils.getWeatherResponse()
        every {
            wordsApiService.getForecast(
                params.endPoint.name,
                params.cityName,
                params.cnt,
                params.units
            )
        } returns Single.just(cityWeatherResponse)

        wordsLocalDataSource.fetchForecast(params)
            .subscribe { stringResponse, t2 ->
                Truth.assertThat(stringResponse).isEqualTo(cityWeatherResponse)
            }

        verify {
            wordsApiService.getForecast(
                params.endPoint.name,
                params.cityName,
                params.cnt,
                params.units
            )
        }
    }

    @Test
    fun fetchForecastSnipet() {
        val params = ForecastParams("Cairo",1,"metric",EndPoint.find)
        val citySnipetWeatherResponse = TestUtils.getWeatherSnipetResponse()
        every {
            wordsApiService.fetchForecastSnipet(
                params.endPoint.name,
                params.cityName,
                params.cnt,
                params.units
            )
        } returns Single.just(citySnipetWeatherResponse)

        wordsLocalDataSource.fetchForecastSnipet(params)
            .subscribe { stringResponse, t2 ->
                Truth.assertThat(stringResponse).isEqualTo(citySnipetWeatherResponse)
            }

        verify {
            wordsApiService.fetchForecastSnipet(
                params.endPoint.name,
                params.cityName,
                params.cnt,
                params.units
            )
        }
    }
}