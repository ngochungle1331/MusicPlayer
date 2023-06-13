package com.fox.training.data.source.remote

import com.fox.training.data.network.ApiService
import com.fox.training.data.network.response.DataResult
import com.fox.training.data.network.response.SearchResult
import com.fox.training.data.source.interf.AppDataSource
import retrofit2.Call

class RemoteDataSource : AppDataSource {
    private val apiService = ApiService

    override suspend fun getSongsRecommend(type: String, id: String): DataResult =
        apiService.api.getSongsRecommend(type, id)

    override suspend fun getChartRealTime(): DataResult = apiService.api.getChartRealTime()
    override suspend fun searchMusic(query: String): SearchResult =
        apiService.apiSearch.searchMusic(query = query)

}