package com.example.zakiahmedkotlin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitBuilder {

    fun  initializeWeatherRetrofit() : ApiInterface {

        val retrofit : ApiInterface = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(ApiInterface::class.java)

        return retrofit
    }
}