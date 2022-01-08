package com.example.booksearchrxjavakotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booksearchrxjavakotlin.model.BookListModel
import com.example.booksearchrxjavakotlin.network.RetroInstance
import com.example.booksearchrxjavakotlin.network.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel: ViewModel() {
    lateinit var bookList: MutableLiveData<BookListModel>

    // call init method
    init {
        bookList = MutableLiveData()
    }

    // creating a separate function which is going to return this
    // book list observable just to update the recycler view in our
    // activity
    fun getBookListObservable(): MutableLiveData<BookListModel> {
        return bookList
    }

    // Function to make API call to retrofit using RxJava
    // We will fetch the query string from the activity
    fun makeApiCall(query: String) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        // The getBookListFromApi returns an observable
        // need to do something with that observable
        retroInstance.getBookListFromApi(query)
            // subsribeOn operator tells the observable which
            // thread to emit and push items on all the way down to the
            // observer. Schedulers.io() is a separate thread pool
            // for non-CPU intensive operations, usually IO stuff like
            // reading from DB, making network call, reading from file, etc
            .subscribeOn(Schedulers.io())
            // Use the main android thread (UI)
            // This is where the observer will observe
            // the getBookListFromApi observable object
            .observeOn(AndroidSchedulers.mainThread())
            // The observer will not observe the observable
            // unless it subscribes to it!
            // The getBookListObserverRx will
            // now subscribe to our getBookListObservable
            .subscribe(getBookListObserverRx())
    }

    fun getBookListObserverRx(): Observer<BookListModel> {
        // Implement the Observer Interface
        // Basically what do we wanna do when we
        // receive the Observable book list?
        return object : Observer<BookListModel> {
            override fun onSubscribe(d: Disposable) {
                // start progress bar / circle
            }

            override fun onNext(t: BookListModel) {
                bookList.postValue(t)
            }

            override fun onError(e: Throwable) {
                // Called from background thread
                bookList.postValue(null)
            }

            override fun onComplete() {
                // hide progress indicator
            }
        }
    }
}