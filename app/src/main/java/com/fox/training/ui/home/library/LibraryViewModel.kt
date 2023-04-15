package com.fox.training.ui.home.library

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fox.training.data.network.response.Music
import kotlinx.coroutines.launch

class LibraryViewModel : ViewModel() {
    private var _listLibraryMusic = MutableLiveData<List<Music>>()
    val listLibraryMusic: LiveData<List<Music>>
        get() = _listLibraryMusic

    fun getLocalMusic(context: Context) {

            val musicList = mutableListOf<Music>()
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
            )
            val collection =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Audio.Media.getContentUri(
                        MediaStore.VOLUME_EXTERNAL
                    )
                } else {
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
            val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

            context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                var position = 0
                while (cursor.moveToNext()) {
                    val id = cursor.getString(idColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val data = cursor.getString(dataColumn)
                    val duration = cursor.getInt(durationColumn) / 1000
                    val music =
                        Music(id, title, artist, data, "", position++, "local", duration, "")
                    musicList.add(music)
                }
            }
            _listLibraryMusic.value = musicList

    }
}