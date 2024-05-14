package com.kimdo.mybooksearchapp.data.repository

import com.kimdo.mybooksearchapp.data.api.RetrofitInstance.api
import com.kimdo.mybooksearchapp.data.model.SearchResponse
import retrofit2.Response

class BookSearchRepositoryImpl : BookSearchRepository{
    override suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): Response<SearchResponse> {
        return api.searchBooks(query, sort, page, size)
    }
}