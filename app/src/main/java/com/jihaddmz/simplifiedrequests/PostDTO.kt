package com.jihaddmz.simplifiedrequests


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class PostDTO(
    @field:[Expose SerializedName("body")]
    val body: String?,
    @field:[Expose SerializedName("id")]
    val id: Int? = 1,
    @field:[Expose SerializedName("title")]
    val title: String?,
    @field:[Expose SerializedName("userId")]
    val userId: Int?
) : Parcelable