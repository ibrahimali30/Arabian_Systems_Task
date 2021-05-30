package com.ibrahim.arabian_task

import android.content.res.AssetManager
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import java.io.IOException
import java.io.InputStream

object Utils{
    val application = InstrumentationRegistry.getInstrumentation().targetContext

    fun produceListOfNotes(): CityWeatherResponse {
        val notes: CityWeatherResponse = Gson()
            .fromJson(
                getNotesFromFile("response_snipet.json"),
                object: TypeToken<CityWeatherResponse>() {}.type
            )
        return notes
    }

    fun getNotesFromFile(fileName: String): String? {
        return readJSONFromAsset(fileName)
    }

    private fun readJSONFromAsset(fileName: String): String? {
        var json: String? = null
        json = try {
            val inputStream: InputStream = (application.assets as AssetManager).open(fileName)
            inputStream.bufferedReader().use{it.readText()}
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


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
                "main",
                "description",
                "name",
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                1622344147
            ),
            ForecastUiModel(
                "main",
                "description",
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