package com.fox.training.data.source.interf

import android.content.Context
import com.fox.training.data.network.response.Music

interface DBSource {
    fun getListMusic(context: Context): List<Music>

    fun getMusicById(musicId: String, context: Context): Music?

    fun insertMusic(music: Music, context: Context)

    fun deleteMusic(music: Music, context: Context)
}