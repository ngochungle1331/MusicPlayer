package com.fox.training.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "MUSIC")
data class Music(
    @SerializedName("id")
    @PrimaryKey
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("artists_names")
    var artistsNames: String?,
    var data: String?,
    @SerializedName("thumbnail")
    var thumbnail: String?,
    @SerializedName("position")
    var position: Int,
    @SerializedName("type")
    var type: String?,
    @SerializedName("duration")
    var duration: Int,
    @SerializedName("order")
    var order: String?
) : Serializable

data class MusicData(
    @SerializedName("items") var items: List<Music>,
    @SerializedName("song") var song: List<Music>
) : Serializable

data class DataResult(
    @SerializedName("data") var data: MusicData
) : Serializable

data class Artist(
    @SerializedName("aliasName")
    val aliasName: String,
    @SerializedName("block")
    val block: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("oaId")
    val oaId: String,
    @SerializedName("thumb")
    val thumb: String
)

data class SearchData(
    @SerializedName("artist")
    var artist: List<Artist>,
    @SerializedName("song")
    var song: List<Music>
)

data class SearchResult(
    @SerializedName("data")
    var searchData: List<SearchData>,
    @SerializedName("result")
    var result: Boolean
)

