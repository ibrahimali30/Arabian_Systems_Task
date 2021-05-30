package com.ibrahim.arabian_task

import android.content.res.AssetManager
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse
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
}