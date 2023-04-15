package com.fox.training.data.source.local

import android.content.Context
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.interf.DBSource

class LocalDataSource : DBSource {

    override suspend fun getListMusic(context: Context): List<Music> =
        LocalDatabase.getInstance(context).localDao().getListMusic()

    override suspend fun getMusicById(musicId: String, context: Context): Music? =
        LocalDatabase.getInstance(context).localDao().getMusicById(musicId)

    override suspend fun insertMusic(music: Music, context: Context) =
        LocalDatabase.getInstance(context).localDao().insertMusic(music)

    override suspend fun deleteMusic(music: Music, context: Context) =
        LocalDatabase.getInstance(context).localDao().deleteMusic(music)
}