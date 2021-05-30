package com.ibrahim.arabian_task.forcast.data.source.local

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.ibrahim.arabian_task.db.WeatherDatabase
import com.ibrahim.arabian_task.utils.RxSchedulerRule
import com.ibrahim.arabian_task.utils.TestUtils
import io.mockk.MockKAnnotations
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
class ForecastLocalDataSourceTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
    lateinit var forecsatLocalDataSource: ForecastLocalDataSource
    private lateinit var forecsatDatabase: WeatherDatabase
    private lateinit var forecsatDao: ForecastDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        forecsatDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        forecsatDao = forecsatDatabase.forecastDao()
        forecsatLocalDataSource =  ForecastLocalDataSource(forecsatDao)
    }

    private val forecsatMok = TestUtils.getForecastUiModeList()[0]


    @Test
    fun getSavedForecasts() {
        forecsatDao.insertForecastUiModel(forecsatMok)
        // Then

        forecsatLocalDataSource.getSavedForecasts()
            .subscribe {
                val savedForecast = it[0]
                Truth.assertThat(savedForecast.name).isEqualTo(forecsatMok.name)
                Truth.assertThat(savedForecast.main).isEqualTo(forecsatMok.main)
                Truth.assertThat(savedForecast.temp).isEqualTo(forecsatMok.temp)
                Truth.assertThat(savedForecast.description).isEqualTo(forecsatMok.description)
                Truth.assertThat(savedForecast.name).isNotEqualTo("a wrong value")
            }
    }

    @Test
    fun testInsertForecastUiModel() {
        val forecastuimodel = forecsatMok.copy(isFavourite = false)
        forecsatLocalDataSource.insertOrDelete(forecastuimodel)

        forecsatLocalDataSource.getSavedForecasts()
            .subscribe ({
                val savedForecast = it.find { it.name == forecastuimodel.name}!!
                Truth.assertThat(savedForecast.name).isEqualTo(forecastuimodel.name)
                Truth.assertThat(savedForecast.description).isEqualTo(forecastuimodel.description)
                Truth.assertThat(savedForecast.temp).isEqualTo(forecastuimodel.temp)
            },{
                it.printStackTrace()
            })

    }

    @Test
    fun testDeleteForecastUiModel() {
        val forecastuimodel = forecsatMok.copy(isFavourite = true)
        forecsatLocalDataSource.insertOrDelete(forecastuimodel)

        forecsatLocalDataSource.getSavedForecasts()
            .subscribe({
                val savedForecast = it.find { it.name == forecastuimodel.name}
                Truth.assertThat(savedForecast).isEqualTo(null)
            },{
                it.printStackTrace()
            })

    }
}