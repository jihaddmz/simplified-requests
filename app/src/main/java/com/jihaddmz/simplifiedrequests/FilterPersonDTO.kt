package com.jihaddmz.simplifiedrequests

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterPersonDTO(
    @field:[Expose SerializedName("count")]
    val count: Int,
    @field:[Expose SerializedName("persons")]
    val persons: List<PersonDTO>
): Parcelable
