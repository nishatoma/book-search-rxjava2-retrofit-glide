package com.example.booksearchrxjavakotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.booksearchrxjavakotlin.adapter.BookListAdapter
import com.example.booksearchrxjavakotlin.model.BookListModel
import com.example.booksearchrxjavakotlin.viewmodel.MainActivityViewModel
import io.reactivex.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel
    lateinit var bookListAdapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSearchBox()
        initRecyclerView()
    }

    private fun initSearchBox() {
        et_book_name.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadAPIData(s.toString())
            }
        })
    }

    private fun initRecyclerView() {
        books_rv.apply {
            // Step 1: get manager
            layoutManager = LinearLayoutManager(this@MainActivity)
            // Define decoration orientation
            val decor = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(decor)
            bookListAdapter = BookListAdapter()
            adapter = bookListAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadAPIData(input: String) {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getBookListObservable().observe(this, {
            if(it != null) {
                //update adapter...
                bookListAdapter.bookListData = it.items
                bookListAdapter.notifyDataSetChanged()
            }
            else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall(input)
    }
}