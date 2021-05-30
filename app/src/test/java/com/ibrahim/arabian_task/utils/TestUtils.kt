package com.ibrahim.arabian_task.utils

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.ibrahim.arabian_task.forcast.data.model.snipet.CitySnipetWeatherResponse
import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.stream.Collectors

object TestUtils {

    fun getJson(jsonFileName: String): String{
        val input: InputStream = this.javaClass.classLoader.getResourceAsStream(jsonFileName)

        val reader = BufferedReader(InputStreamReader(input))

        val json: String = reader.lines()
            .parallel()
            .collect(Collectors.joining())

        return json
    }


    fun getWeatherResponse(): CityWeatherResponse{
        val notes: CityWeatherResponse = Gson()
            .fromJson(
                getJson("city_forecast_response.json"),
                object: TypeToken<CityWeatherResponse>() {}.type
            )

        return notes
    }

    fun getWeatherSnipetResponse(): CitySnipetWeatherResponse{
        val notes: CitySnipetWeatherResponse = Gson()
            .fromJson(
                getJson("response_snipet.json"),
                object: TypeToken<CitySnipetWeatherResponse>() {}.type
            )

        return notes
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