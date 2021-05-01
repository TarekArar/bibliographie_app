package com.arar_laidi.bibloGraphie

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
   @StringRes val name : Int,
   @StringRes val description : Int,
   val image : String
) : Parcelable