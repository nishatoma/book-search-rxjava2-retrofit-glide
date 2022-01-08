package com.example.booksearchrxjavakotlin.network

import com.example.booksearchrxjavakotlin.model.BookListModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("/volumes")
    // The return type needs to be an observable
    fun getBookListFromApi(@Query("q") query: String): Observable<BookListModel>
}