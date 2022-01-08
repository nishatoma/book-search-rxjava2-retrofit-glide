package com.example.booksearchrxjavakotlin.model

import com.google.gson.annotations.SerializedName

data class Volume(@SerializedName("volumeInfo") val book: Book)
