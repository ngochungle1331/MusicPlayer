package com.fox.training.data.source.local

import android.content.Context
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.interf.DBSource

class LocalDataSource : DBSource {

    override fun getListMusic(context: Context): List<Music> =
        LocalDatabase.getInstance(context).localDao().getListMusic()

    override fun getMusicById(musicId: String, context: Context): Music? =
        LocalDatabase.getInstance(context).localDao().getMusicById(musicId)

    override fun insertMusic(music: Music, context: Context) =
        LocalDatabase.getInstance(context).localDao().insertMusic(music)

    override fun deleteMusic(music: Music, context: Context) =
        LocalDatabase.getInstance(context).localDao().deleteMusic(music)
}