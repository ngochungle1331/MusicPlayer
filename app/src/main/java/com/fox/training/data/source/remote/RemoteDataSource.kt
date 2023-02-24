package com.fox.training.data.source.remote

import com.fox.training.data.network.ApiService
import com.fox.training.data.network.response.DataResult
import com.fox.training.data.source.interf.AppDataSource
import retrofit2.Call

class RemoteDataSource : AppDataSource {

    override fun getSongsRecommend(type: String, id: String): Call<DataResult> {
        return ApiService.api.getSongsRecommend(type, id)
    }

    override fun getChartRealTime(): Call<DataResult> {
        return ApiService.api.getChartRealTime()
    }
}