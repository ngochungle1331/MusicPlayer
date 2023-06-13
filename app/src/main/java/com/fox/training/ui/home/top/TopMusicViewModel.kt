package com.fox.training.ui.home.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.Repository
import kotlinx.coroutines.launch

class TopMusicViewModel : ViewModel() {
    private val _listTopMusic = MutableLiveData<List<Music>>()
    val listTopMusic: LiveData<List<Music>>
        get() = _listTopMusic
    private val repository = Repository()

    fun getTopMusic() {
        viewModelScope.launch {
            val response = repository.getChartRealTime()
            response.data.song.let {
                _listTopMusic.value = it
            }
        }
    }

    fun searchMusic(query: String) {
        viewModelScope.launch {
            val searchResult = repository.searchMusic(query)
            val data = searchResult.searchData

            if (data != null && data.isNotEmpty()) {
                val songs = data[0].song
                _listTopMusic.value = songs
            } else {
                val response = repository.getChartRealTime()
                response.data.song?.let {
                    _listTopMusic.value = it
                }
            }
        }
    }



}