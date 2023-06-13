package com.fox.training.ui.play

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.Repository
import kotlinx.coroutines.launch

class PlayMusicViewModel : ViewModel() {
    private val mutableListMusic = MutableLiveData<List<Music>>()
    val listRecommendMusic: LiveData<List<Music>>
        get() = mutableListMusic
    private val repository = Repository()
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean>
        get() = _isFavorite

    fun getRecommendedMusic(type: String, id: String) {
        viewModelScope.launch {
            val response = repository.getSongsRecommend(type, id)
            response.data.items.let {
                mutableListMusic.value = it
            }
        }
    }

    fun getMusicById(music: Music, context: Context) {
        viewModelScope.launch {
            _isFavorite.postValue(repository.getMusicById(music.id, context) != null)
        }
    }

    fun insertMusic(music: Music, context: Context) {
        viewModelScope.launch {
            repository.insertMusic(music, context)
            _isFavorite.postValue(repository.getMusicById(music.id, context) != null)
        }
    }

    fun deleteMusic(music: Music, context: Context) {
        viewModelScope.launch {
            repository.deleteMusic(music, context)
            _isFavorite.postValue(repository.getMusicById(music.id, context) != null)
        }
    }

}