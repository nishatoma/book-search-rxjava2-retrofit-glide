package com.example.booksearchrxjavakotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksearchrxjavakotlin.R
import com.example.booksearchrxjavakotlin.model.Volume
import kotlinx.android.synthetic.main.recycler_book_row.view.*

class BookListAdapter : RecyclerView.Adapter<BookListAdapter.BookListViewHolder>() {


    var bookListData = ArrayList<Volume>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_book_row, parent, false)

        return BookListViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.bind(bookListData[position])
    }

    override fun getItemCount(): Int {
        return bookListData.size
    }

    class BookListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val bookTitle = view.tvTitle
        private val bookDesc = view.tvDescription
        private val bookPub = view.tvPublisher
        private val bookImage = view.iv_thumb

        fun bind(data: Volume) {
            bookTitle.text = data.book.title
            bookDesc.text = data.book.description
            bookPub.text = data.book.publisher

            val url = data.book?.imageLinks?.smallThumbnail

            // Use glide
            Glide.with(bookImage)
                .load(url)
                .circleCrop()
                .into(bookImage)
        }
    }
}