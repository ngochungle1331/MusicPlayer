package com.fox.training.data.source

import android.content.Context
import com.fox.training.data.network.response.DataResult
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.interf.AppDataSource
import com.fox.training.data.source.interf.DBSource
import com.fox.training.data.source.local.LocalDataSource
import com.fox.training.data.source.local.LocalDatabase
import com.fox.training.data.source.remote.RemoteDataSource
import retrofit2.Call

class Repository : AppDataSource, DBSource {
    private val remoteDataSource = RemoteDataSource()

    override fun getSongsRecommend(type: String, id: String): Call<DataResult> =
        remoteDataSource.getSongsRecommend(type, id)

    override fun getChartRealTime(): Call<DataResult> = remoteDataSource.getChartRealTime()

    override fun getListMusic(context: Context): List<Music> =
        LocalDataSource(LocalDatabase.getInstance(context).LocalDao()).getListMusic(context)

    override fun getMusicById(musicId: String, context: Context): Music? =
        LocalDataSource(LocalDatabase.getInstance(context).LocalDao()).getMusicById(musicId, context)

    override fun insertMusic(music: Music, context: Context) =
        LocalDataSource(LocalDatabase.getInstance(context).LocalDao()).insertMusic(music, context)

    override fun deleteMusic(music: Music, context: Context) =
        LocalDataSource(LocalDatabase.getInstance(context).LocalDao()).deleteMusic(music, context)
}