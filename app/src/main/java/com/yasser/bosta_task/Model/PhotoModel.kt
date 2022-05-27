package com.yasser.bosta_task.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.yasser.bosta_task.Utils.Interfaces.ModelBase


data class PhotoModel(
        @SerializedName("albumId")
        var albumId: String,
        @SerializedName("id")
        override var id: String,
        @SerializedName("title")
        var title: String,
        @SerializedName("url")
        var url: String,
        @SerializedName("thumbnailUrl")
        var thumbnailUrl: String,
):ModelBase