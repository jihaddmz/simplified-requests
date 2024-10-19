package com.jihaddmz.simplifiedrequests


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class PersonDTO(
    @field:[Expose SerializedName("firstName")]
    val firstName: String?,
    @field:[Expose SerializedName("lastName")]
    val lastName: String?
) : Parcelable