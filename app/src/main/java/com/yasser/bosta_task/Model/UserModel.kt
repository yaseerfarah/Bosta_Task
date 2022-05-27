package com.yasser.bosta_task.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName




data class UserModel(
        @SerializedName("id")
        var id: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("username")
        var username: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("address")
        var address: AddressModel,

        var albumList:MutableList<AlbumModel>?,





)