package com.yasser.bosta_task.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName




data class AddressModel(
        @SerializedName("street")
        var street: String,
        @SerializedName("suite")
        var suite: String,
        @SerializedName("city")
        var city: String,
        @SerializedName("zipcode")
        var zipcode: String,
)