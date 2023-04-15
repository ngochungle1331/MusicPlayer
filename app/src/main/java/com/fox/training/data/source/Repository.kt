package com.fox.training.data.source

import android.content.Context
import com.fox.training.data.network.response.DataResult
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.interf.AppDataSource
import com.fox.training.data.source.interf.DBSource
import com.fox.training.data.source.local.LocalDataSource
import com.fox.training.data.source.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository : AppDataSource, DBSource {
    private val remoteDataSource = RemoteDataSource()
    private val localDataSource = LocalDataSource()

    override suspend fun getSongsRecommend(type: String, id: String): DataResult {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getSongsRecommend(type, id)
        }
    }

    override suspend fun getChartRealTime(): DataResult {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getChartRealTime()
        }
    }

    override suspend fun getListMusic(context: Context): List<Music> {
        return withContext(Dispatchers.IO) {
            localDataSource.getListMusic(context)
        }
    }

    override suspend fun getMusicById(musicId: String, context: Context): Music? {
        return withContext(Dispatchers.IO) {
            localDataSource.getMusicById(musicId, context)
        }
    }

    override suspend fun insertMusic(music: Music, context: Context) {
        return withContext(Dispatchers.IO) {
            localDataSource.insertMusic(music, context)
        }
    }

    override suspend fun deleteMusic(music: Music, context: Context) {
        return withContext(Dispatchers.IO) {
            localDataSource.deleteMusic(music, context)
        }
    }
}