package com.kimdo.mybooksearchapp.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.databinding.ItemBookPreviewBinding

class BookSearchAdapter: ListAdapter<Book, BookSearchViewHolder>(BookDiffCallback){
    companion object {
        private val BookDiffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return BookSearchViewHolder(
            ItemBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        val book = currentList[position]
        holder.bind( book )
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(book ) }
        }
    }

    private var onItemClickListener: ((Book) -> Unit)? = null
    fun setOnItemClickListener(listener: (Book) -> Unit ) {
        onItemClickListener = listener
    }
}