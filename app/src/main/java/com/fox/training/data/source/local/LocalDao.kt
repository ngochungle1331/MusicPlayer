package com.fox.training.data.source.local

import androidx.room.*
import com.fox.training.data.network.response.Music

@Dao
interface LocalDao {
    @Query("SELECT * FROM MUSIC")
    suspend fun getListMusic() : List<Music>

    @Query("SELECT * FROM MUSIC WHERE id = :musicId")
    suspend fun getMusicById(musicId: String) : Music?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusic(music: Music)

    @Delete
    suspend fun deleteMusic(music: Music)

}