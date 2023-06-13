package com.fox.training.data.source.interf

import com.fox.training.data.network.response.DataResult
import com.fox.training.data.network.response.SearchResult
import retrofit2.http.Query

interface AppDataSource {
    suspend fun getSongsRecommend(type: String, id: String): DataResult

    suspend fun getChartRealTime(): DataResult

    suspend fun searchMusic(query: String): SearchResult

}