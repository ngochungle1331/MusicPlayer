package com.fox.training.data.source.interf

import com.fox.training.data.network.response.DataResult

interface AppDataSource {
    suspend fun getSongsRecommend(type: String, id: String): DataResult

    suspend fun getChartRealTime(): DataResult
}