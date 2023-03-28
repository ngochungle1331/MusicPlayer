package com.fox.training.ui.home.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.Repository

class FavoriteViewModel : ViewModel() {
    private val _listFavoriteMusic = MutableLiveData<List<Music>>()
    private val repository = Repository()
    val listFavoriteMusic: LiveData<List<Music>>
        get() = _listFavoriteMusic

    fun getListMusic(context: Context) {
        Thread {
            _listFavoriteMusic.postValue(repository.getListMusic(context))
        }.start()
    }
}