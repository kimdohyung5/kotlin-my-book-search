package com.kimdo.mybooksearchapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.databinding.ItemBookPreviewBinding
import java.util.Date

class BookSearchViewHolder(val binding: ItemBookPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind( book : Book) {

        val author = book.authors.toString().removeSurrounding("[", "]")
        val publisher = book.publisher
        val date = if( book.datetime.isNotEmpty()) book.datetime.substring(0, 10) else ""

        binding.ivArticleImage.load(book.thumbnail)
        binding.tvTitle.text = book.title
        binding.tvAuthor.text = "${author} | ${publisher}"
        binding.tvDatetime.text = date
    }
}
