package com.fox.training.data.network

import com.fox.training.data.network.response.DataResult
import com.fox.training.data.network.response.SearchResult
import com.fox.training.util.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

interface ApiService {
    @GET("xhr/recommend")
    suspend fun getSongsRecommend(
        @Query("type") type: String, @Query("id") id: String
    ): DataResult

    @GET("xhr/chart-realtime")
    suspend fun getChartRealTime(): DataResult

    @GET("complete")
    suspend fun searchMusic(
        @Query("type") type: String = "artist,song,key,code",
        @Query("num") num: Int = 500,
        @Query("query") query: String
    ): SearchResult

    companion object {
        val api: ApiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(AppConstants.BASE_URL)
            .build()
            .create(ApiService::class.java)

        val apiSearch: ApiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(AppConstants.SEARCH_BASE_URL)
            .build()
            .create(ApiService::class.java)
    }
}