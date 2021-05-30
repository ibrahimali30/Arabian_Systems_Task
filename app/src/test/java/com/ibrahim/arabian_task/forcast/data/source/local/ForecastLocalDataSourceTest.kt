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
    lateinit var wordsLocalDataSource: ForecastLocalDataSource
    private lateinit var wordsDatabase: WeatherDatabase
    private lateinit var wordsDao: ForecastDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        wordsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        wordsDao = wordsDatabase.forecastDao()
        wordsLocalDataSource =  ForecastLocalDataSource(wordsDao)
    }

    private val wordsMok = TestUtils.getForecastUiModeList()[0]


    @Test
    fun getSavedForecasts() {
        wordsDao.insertForecastUiModel(wordsMok)
        // Then

        wordsLocalDataSource.getSavedForecasts()
            .subscribe {
                val savedForecast = it[0]
                Truth.assertThat(savedForecast.name).isEqualTo(wordsMok.name)
                Truth.assertThat(savedForecast.main).isEqualTo(wordsMok.main)
                Truth.assertThat(savedForecast.temp).isEqualTo(wordsMok.temp)
                Truth.assertThat(savedForecast.description).isEqualTo(wordsMok.description)
                Truth.assertThat(savedForecast.name).isNotEqualTo("a wrong value")
            }
    }

    @Test
    fun testInsertForecastUiModel() {
        val forecastuimodel = wordsMok.copy(isFavourite = false)
        wordsLocalDataSource.insertOrDelete(forecastuimodel)

        wordsLocalDataSource.getSavedForecasts()
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
        val forecastuimodel = wordsMok.copy(isFavourite = true)
        wordsLocalDataSource.insertOrDelete(forecastuimodel)

        wordsLocalDataSource.getSavedForecasts()
            .subscribe({
                val savedForecast = it.find { it.name == forecastuimodel.name}
                Truth.assertThat(savedForecast).isEqualTo(null)
            },{
                it.printStackTrace()
            })

    }
}