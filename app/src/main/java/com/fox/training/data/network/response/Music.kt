package com.fox.training.data.network.response

import com.google.gson.annotations.SerializedName

data class Music(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("artists_names")
    val artistsNames: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("order")
    val order: String
) : java.io.Serializable


data class MusicData(
    @SerializedName("items") val items: List<Music>,
    @SerializedName("song") val song: List<Music>
) : java.io.Serializable

data class DataResult(
    @SerializedName("data") val data: MusicData
) : java.io.Serializable

