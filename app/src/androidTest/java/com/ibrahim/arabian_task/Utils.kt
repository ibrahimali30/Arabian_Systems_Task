package com.ibrahim.arabian_task

import androidx.test.platform.app.InstrumentationRegistry
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel

object Utils{
    val application = InstrumentationRegistry.getInstrumentation().targetContext


    fun getForecastUiModeList(): List<ForecastUiModel> {
        return listOf(
            ForecastUiModel(
                "main 1",
                "description 1",
                "name 1",
                10.0,
                0.10,
                0.10,
                03.0,
                01.0,
                1622344147
            ),
            ForecastUiModel(
                "main 2",
                "description 2",
                "name",
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                1622344147
            ),
            ForecastUiModel(
                "main3 ",
                "description 3",
                "name",
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                1622344147
            )

        )
    }
}



//fun getForecastUiModeList(): List<ForecastUiModel> {
//
//    return Utils.produceListOfNotes().list.map { weatherMain ->
//        val weather = weatherMain.weather[0]
//        val main = weatherMain.main
//        ForecastUiModel(
//            weather.main,
//            weather.description,
//            weatherMain.name,
//            main.temp,
//            main.feels_like,
//            main.temp_max,
//            main.temp_min,
//            weatherMain.wind.speed,
//            weatherMain.dt,
//            false
//        )
//    }
//
//}