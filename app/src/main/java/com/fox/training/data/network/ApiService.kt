package com.fox.training.data.network

import com.fox.training.data.network.response.DataResult
import com.fox.training.util.AppConstants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("xhr/recommend")
    fun getSongsRecommend(@Query("type") type: String,@Query("id") id: String): Call<DataResult>

    @GET("xhr/chart-realtime")
    fun getChartRealTime(): Call<DataResult>

    companion object {
        val api: ApiService = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppConstants.baseUrl)
            .build().create(ApiService::class.java)
    }
}