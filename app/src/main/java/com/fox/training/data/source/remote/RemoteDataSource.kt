package com.fox.training.data.source.remote

import com.fox.training.data.network.ApiService
import com.fox.training.data.network.response.DataResult
import com.fox.training.data.source.interf.AppDataSource
import retrofit2.Call

class RemoteDataSource : AppDataSource {
    private val apiService = ApiService

    override fun getSongsRecommend(type: String, id: String): Call<DataResult> =
        apiService.api.getSongsRecommend(type, id)

    override fun getChartRealTime(): Call<DataResult> = apiService.api.getChartRealTime()
}