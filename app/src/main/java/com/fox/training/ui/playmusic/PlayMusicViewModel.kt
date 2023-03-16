package com.fox.training.ui.playmusic

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fox.training.data.network.response.DataResult
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayMusicViewModel : ViewModel() {
    val mutableListMusic = MutableLiveData<List<Music>>()
    val listRecommendMusic: LiveData<List<Music>>
        get() = mutableListMusic
    private val repository = Repository()
    private val mutableIsFavorite = MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean>
        get() = mutableIsFavorite

    fun getRecommendedMusic(type: String, id: String) {
        repository.getSongsRecommend(type, id)
            .enqueue(object : Callback<DataResult> {
                override fun onResponse(call: Call<DataResult>, response: Response<DataResult>) {
                    response.body()?.data?.items?.let {
                        mutableListMusic.value = it
                    }
                }

                override fun onFailure(call: Call<DataResult>, t: Throwable) {
                }
            })
    }

    fun getMusicById(music: Music, context: Context) {
        Thread {
            mutableIsFavorite.postValue(repository.getMusicById(music.id, context) != null)
        }.start()
    }

    fun insertMusic(music: Music, context: Context) {
        Thread {
            repository.insertMusic(music, context)
            getMusicById(music, context)

        }.start()
    }

    fun deleteMusic(music: Music, context: Context) {
        Thread {
            repository.deleteMusic(music, context)
            getMusicById(music, context)
        }.start()
    }

}