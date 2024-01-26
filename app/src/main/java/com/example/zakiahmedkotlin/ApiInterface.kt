package com.example.zakiahmedkotlin

import com.example.zakiahmedkotlin.ui.Weather.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getWeatherData (
    @Query ("q") city : String,
        @Query ("appid") appid : String,
        @Query ("units") units : String ) : Call< WeatherModel>

//    @GET("top-headlines")
//    fun getNews (
//        @Query ("country") country : String,
//        @Query ("pageSize") pageSize : Int,
//        @Query ("apiKey") apikey : String
//    ) : Call<>
}