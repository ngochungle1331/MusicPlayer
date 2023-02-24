package com.fox.training.data.source.interf

import com.fox.training.data.network.response.DataResult
import retrofit2.Call

interface AppDataSource {
    fun getSongsRecommend(type: String, id: String): Call<DataResult>
    fun getChartRealTime(): Call<DataResult>
}