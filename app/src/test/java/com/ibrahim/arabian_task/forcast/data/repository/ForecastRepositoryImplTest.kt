package com.ibrahim.arabian_task.forcast.data.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.ibrahim.arabian_task.db.WeatherDatabase
import com.ibrahim.arabian_task.forcast.data.source.local.ForecastDao
import com.ibrahim.arabian_task.forcast.data.source.local.ForecastLocalDataSource
import com.ibrahim.arabian_task.forcast.data.source.remote.ForecastRemoteDataSource
import com.ibrahim.arabian_task.forcast.domain.entity.EndPoint
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.utils.RxSchedulerRule
import com.ibrahim.arabian_task.utils.TestUtils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class ForecastRepositoryImplTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
    @MockK
    lateinit var forecsatLocalDataSource: ForecastLocalDataSource
    @MockK
    lateinit var forecsatRemoteDataSource: ForecastRemoteDataSource
    private lateinit var forecsatDatabase: WeatherDatabase
    private lateinit var forecsatDao: ForecastDao
    private lateinit var forecsatRepository: ForecastRepositoryImpl

    private val forecsatMok = TestUtils.getForecastUiModeList()[0]

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        forecsatDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        forecsatDao = forecsatDatabase.forecastDao()
        forecsatRepository = ForecastRepositoryImpl(forecsatRemoteDataSource, forecsatLocalDataSource)

    }

    @Test
    fun testGetForecastFromLocalDB() {
        val forecastuimodel = forecsatMok.copy(isFavourite = true)

        every {
            forecsatLocalDataSource.getSavedForecasts()
        } returns Flowable.just(listOf(forecastuimodel))

        forecsatRepository.getForecastFromLocalDB()
            .subscribe ({
                val savedForecast = it.find { it.name == forecastuimodel.name}!!
                Truth.assertThat(savedForecast.name).isEqualTo(forecastuimodel.name)
            },{

            })

        verify {
            forecsatLocalDataSource.getSavedForecasts()
        }
    }

    @Test
    fun testFetchForecast() {
        //mocked response json
        /*
  "cod": "200",
  "count": 1,
  "list": [
    {
      "id": 360630,
      "name": "Cairo",
      "coord": {
        "lat": 30.0626,
        "lon": 31.2497
      },
      "main": {
        "temp": 21.98,.......*/

        val params = ForecastParams("Cairo",1,"metric", EndPoint.find)
        val response = TestUtils.getWeatherResponse()

        every {
            forecsatRemoteDataSource.fetchForecast(params)
        } returns Single.just(response)

        forecsatRepository.fetchForecast(params)
            .subscribe ({
                //Cairo and 21.98 are the values from the test json file city_forecast_response.json
                val savedForecast = it.find { it.name == "Cairo"}!!
                Truth.assertThat(savedForecast.name).isEqualTo("Cairo")
                Truth.assertThat(savedForecast.temp).isEqualTo(21.98)
            },{

            })

        verify {
            forecsatRemoteDataSource.fetchForecast(params)
        }
    }
}