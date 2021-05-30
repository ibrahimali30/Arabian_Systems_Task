package com.ibrahim.arabian_task

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
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
                getJson("response_snipet.json"),
                object: TypeToken<CityWeatherResponse>() {}.type
            )

        return notes
    }


}