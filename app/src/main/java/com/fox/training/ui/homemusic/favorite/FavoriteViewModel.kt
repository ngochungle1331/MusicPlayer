package com.fox.training.ui.homemusic.favorite

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.Repository

class FavoriteViewModel : ViewModel() {
    private val mutableListMusic = MutableLiveData<List<Music>>()
    private val repository = Repository()
    val listFavoriteMusic: LiveData<List<Music>>
        get() = mutableListMusic

    fun getListMusic(context: Context) {
        Thread {
            mutableListMusic.postValue(repository.getListMusic(context))
        }.start()
    }

}