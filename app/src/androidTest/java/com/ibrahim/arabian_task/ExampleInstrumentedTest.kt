package com.ibrahim.arabian_task

import android.content.res.AssetManager
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.ibrahim.arabian_task.forcast.data.model.weather.CityWeatherResponse

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.IOException
import java.io.InputStream

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ibrahim.Arabian_Systems_Task", appContext.packageName)
    }
}