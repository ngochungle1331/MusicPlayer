package com.fox.training.data.source.local

import androidx.room.*
import com.fox.training.data.network.response.Music

@Dao
interface LocalDao {
    @Query("SELECT * FROM MUSIC")
    fun getListMusic() : List<Music>

    @Query("SELECT * FROM MUSIC WHERE id = :musicId")
    fun getMusicById(musicId: String) : Music?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(music: Music)

    @Delete
    fun deleteMusic(music: Music)

}