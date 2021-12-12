package com.example.bookapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookListing(
    val book:Book,
    val price:Int,
    val timestamp: Long = System.currentTimeMillis()
):Parcelable{

}
