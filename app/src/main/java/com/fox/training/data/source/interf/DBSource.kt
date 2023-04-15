package com.fox.training.data.source.interf

import android.content.Context
import com.fox.training.data.network.response.Music

interface DBSource {
    suspend fun getListMusic(context: Context): List<Music>

    suspend fun getMusicById(musicId: String, context: Context): Music?

    suspend fun insertMusic(music: Music, context: Context)

    suspend fun deleteMusic(music: Music, context: Context)
}