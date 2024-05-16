package com.kimdo.mybooksearchapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kimdo.mybooksearchapp.data.model.Book

class BookSearchPagingSource(
    private val query: String,
    private val sort: String,
) : PagingSource<Int, Book>(){
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        TODO("Not yet implemented")
    }
}