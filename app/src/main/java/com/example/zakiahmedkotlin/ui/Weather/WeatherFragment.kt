package com.example.zakiahmedkotlin.ui.Weather

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zakiahmedkotlin.Manifest
import com.example.zakiahmedkotlin.R
import com.example.zakiahmedkotlin.RetrofitBuilder
import com.example.zakiahmedkotlin.databinding.FragmentWeatherBinding
import com.google.android.material.search.SearchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private var cityName = "Waterloo"
    val lOCATION_REQUEST = android.Manifest.permission.ACCESS_FINE_LOCATION


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val thread : Thread = Thread(Runnable {
            fetchWeatherData(cityName)
        })

        thread.run()

        val arguments = arguments
        if (arguments != null) {
            val city = arguments.getString("cityName")
                fetchWeatherData(city!!)

        }


//        searchCity()



        return root
    }

    private fun getCityFromLocation() {
        val geoCoder = activity?.let { Geocoder( it.baseContext, Locale.getDefault() ) }
//        List<Address>
    }


    private fun getLastLocation() {
        if (activity?.let { ActivityCompat.checkSelfPermission(it.applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) } == PackageManager.PERMISSION_GRANTED) {

        }

    }

//    private fun searchCity() {

//        binding.weatherSearchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let { fetchWeatherData(it) }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//
//        })
//    }

    private fun fetchWeatherData(cityName: String) {
        val retrofitBuilder = RetrofitBuilder()

        val apiInterface  = retrofitBuilder.initializeWeatherRetrofit()
        GlobalScope.launch {
            val apiCallResponse : Call<WeatherModel> = apiInterface.getWeatherData(cityName,"cdd3f5aea1434bd19179afd246c4ea51", "metric" )
            apiCallResponse.enqueue(object: Callback<WeatherModel> {
                override fun onResponse( call: Call<WeatherModel>, response: Response<WeatherModel>) {
                    getResponseAndDisplayData(response, cityName)
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        }

    }

    private fun getResponseAndDisplayData(response: Response<WeatherModel>, cityName: String) {
        val responseBody : WeatherModel? = response.body()
        if (response.isSuccessful && responseBody != null) {
            val temperature = responseBody.main.temp.toInt()
            val humidity = responseBody.main.humidity
            val windSpeed = responseBody.wind.speed
            val sunRise = responseBody.sys.sunrise.toLong()
            val sunSet = responseBody.sys.sunset.toLong()
            val seaLevel = responseBody.main.pressure
            val condition = responseBody.weather.firstOrNull()?.main?: "Unknown"
            val maxTemp = responseBody.main.temp_max
            val minTemp = responseBody.main.temp_min

            binding.tvTemp.text = "$temperature 0C"
            binding.tvWeatherCondition.text = condition
            binding.tvMaxTemp.text = "Max: $maxTemp"
            binding.tvMinTemp.text = "Min: $minTemp"
            binding.tvHumidity.text = "$humidity %"
            binding.tvWindSpeed.text = "$windSpeed m/s"
            binding.tvSunRise.text = convertTimeToHoursAndMin(sunRise)
            binding.tvSunSet.text = convertTimeToHoursAndMin(sunSet)
            binding.tvSeaLevel.text = "$seaLevel hPa"
            binding.tvWeather.text = condition
            binding.tvLocation.text = cityName
            binding.tvDayName.text = getDayName()
            binding.tvTodayDate.text = getTodayDate()

            checkWeatherCondition(condition)
        }

    }

    private fun checkWeatherCondition(condition: String) {
        when (condition) {
            "Clear Sky", "Sunny", "Clear" -> {
                binding.weatherConstraintLayout.setBackgroundResource(R.drawable.sunny_background)
                binding.animatedImage.setAnimation(R.raw.sun)
            }

            "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> {
                binding.weatherConstraintLayout.setBackgroundResource(R.drawable.colud_background)
                binding.animatedImage.setAnimation(R.raw.cloud)
            }

            "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> {
                binding.weatherConstraintLayout.setBackgroundResource(R.drawable.rain_background)
                binding.animatedImage.setAnimation(R.raw.rain)
            }

            "Light Snow", "Moderate Snow", "Heavy Snow", "Bilzzard" -> {
                binding.weatherConstraintLayout.setBackgroundResource(R.drawable.snow_background)
                binding.animatedImage.setAnimation(R.raw.snow)
            }

            else -> {
                binding.weatherConstraintLayout.setBackgroundResource(R.color.white)
                binding.animatedImage.setAnimation(R.raw.cloud)
            }
        }
        binding.animatedImage.playAnimation()
    }


    private fun convertTimeToHoursAndMin(timeStamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timeStamp * 1000))

    }
    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())

    }

    private fun getDayName() :String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}