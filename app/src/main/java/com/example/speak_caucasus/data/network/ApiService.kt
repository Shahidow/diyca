package com.example.speak_caucasus.data.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/")
    fun ping(): Call<Unit> // Используем Unit, если тело запроса пустое
}

fun sendPingRequest() {
    val apiService = RetrofitClient.instance.create(ApiService::class.java)
    val call = apiService.ping() // Отправляем POST-запрос

    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful) {
                Log.i("123", "!!!!!!!!!!!!")
            } else {
                Log.i("123", "Error: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.i("123", "Failure: ${t.message}")
        }
    })
}