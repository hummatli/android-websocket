package com.challenge.app.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stock(
    val name: String,
    val isin: String,
    var price: String? = null
): Parcelable
