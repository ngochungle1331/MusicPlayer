package com.fox.training.ui.main.fragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fox.training.data.network.response.DataResult
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.remote.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicViewModel : ViewModel() {
    val listMusicLiveData = MutableLiveData<List<Music>>()

    fun getTopMusic() {
        RemoteDataSource().getChartRealTime().enqueue(object : Callback<DataResult> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<DataResult>, response: Response<DataResult>) {
                response.body()?.data?.song.let {
                    listMusicLiveData.value = it
                }
            }

            override fun onFailure(call: Call<DataResult>, t: Throwable) {
                Log.d("Get data failed: ", t.message.toString())
            }

        })
    }

    fun getRecommendedMusic(music: Music) {
        RemoteDataSource().getSongsRecommend(music.type ,music.id).enqueue(object : Callback<DataResult> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<DataResult>, response: Response<DataResult>) {
                listMusicLiveData.run {
                    response.body()?.data?.items?.let {
                        listMusicLiveData.value = it
                    }
                }
            }

            override fun onFailure(call: Call<DataResult>, t: Throwable) {
                Log.d("Get data failed: ", t.message.toString())
            }
        })
    }

}