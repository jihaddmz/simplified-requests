package com.jihaddmz.simplifiedrequests


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class SimpleMessageDTO(
    @field:[Expose SerializedName("message")]
    val message: String?,
    @field:[Expose SerializedName("date")]
    val date: String?,
) : Parcelable