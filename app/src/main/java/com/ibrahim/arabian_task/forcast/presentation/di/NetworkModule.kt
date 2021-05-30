package com.ibrahim.arabian_task.forcast.presentation.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit.Builder {
        val okHttpClient = getCommonOkHttpClient()
        val builder = Retrofit.Builder()
        val gson = GsonBuilder().enableComplexMapKeySerialization()
            .setLenient()
            .create()
        builder.baseUrl("https://community-open-weather-map.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        builder.client(okHttpClient)

        return builder
    }


    private fun getCommonOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        httpClient.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("x-rapidapi-key", "8bb27d675amshaefdcb3c1ba3e4ap121730jsn520bdbe89028")
            requestBuilder.header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
            chain.proceed(requestBuilder.build())
        })
        return httpClient.build()
    }


}