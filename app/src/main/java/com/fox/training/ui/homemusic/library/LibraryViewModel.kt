package com.fox.training.ui.homemusic.library

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fox.training.data.network.response.Music
import com.fox.training.data.source.Repository

class LibraryViewModel : ViewModel() {
    private val mutableListMusic = MutableLiveData<List<Music>>()
    val listLibraryMusic: LiveData<List<Music>>
        get() = mutableListMusic

}