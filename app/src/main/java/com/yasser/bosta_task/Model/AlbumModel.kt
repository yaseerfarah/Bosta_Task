package com.yasser.bosta_task.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.yasser.bosta_task.Utils.Interfaces.ModelBase
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumModel(
        @SerializedName("userId")
        var userId: String,
        @SerializedName("id")
        override var id: String,
        @SerializedName("title")
        var title: String,
):ModelBase, Parcelable