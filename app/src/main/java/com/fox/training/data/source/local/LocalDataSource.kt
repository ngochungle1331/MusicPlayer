package com.fox.training.data.source.local

import android.content.Context
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.interf.DBSource

class LocalDataSource(private var localDao: LocalDao) : DBSource {
    override fun getListMusic(context: Context): List<Music> {
        return localDao.getListMusic()
    }

    override fun getMusicById(musicId: String, context: Context): Music? {
        return localDao.getMusicById(musicId)
    }

    override fun insertMusic(music: Music, context: Context) {
        localDao.insertMusic(music)
    }

    override fun deleteMusic(music: Music, context: Context) {
        localDao.deleteMusic(music)
    }

}