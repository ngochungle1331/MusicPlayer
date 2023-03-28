package com.fox.training.ui.home.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fox.training.data.network.response.DataResult
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopMusicViewModel : ViewModel() {
    private val _listTopMusic = MutableLiveData<List<Music>>()
    val listTopMusic: LiveData<List<Music>>
        get() = _listTopMusic
    private val repository = Repository()

    fun getTopMusic() {
        repository.getChartRealTime().enqueue(object : Callback<DataResult> {
            override fun onResponse(call: Call<DataResult>, response: Response<DataResult>) {
                response.body()?.data?.song.let {
                    _listTopMusic.value = it
                }
            }

            override fun onFailure(call: Call<DataResult>, t: Throwable) = Unit
        })
    }
}