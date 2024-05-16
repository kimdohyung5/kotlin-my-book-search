package com.kimdo.mybooksearchapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import com.kimdo.mybooksearchapp.data.api.RetrofitInstance.api
import com.kimdo.mybooksearchapp.data.db.BookSearchDatabase
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.data.model.SearchResponse
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepositoryImpl.PreferenceKeys.SORT_MODE
import com.kimdo.mybooksearchapp.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response

class BookSearchRepositoryImpl (
    private val db: BookSearchDatabase,
    private val dataStore: DataStore<Preferences>
): BookSearchRepository{
    override suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): Response<SearchResponse> {
        return api.searchBooks(query, sort, page, size)
    }

    override suspend fun insertBooks(book: Book) {
        db.bookSearchDao().insertBook(book)
    }

    override suspend fun deleteBooks(book: Book) {
        db.bookSearchDao().deleteBook(book)
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return db.bookSearchDao().getFavoriteBooks()
    }

    override suspend fun saveSortMode(mode: String) {
        dataStore.edit { prefs ->
            prefs[SORT_MODE] = mode
        }
    }

    override suspend fun getSortMode(): Flow<String> {
        return dataStore.data
            .catch {  exception ->
                if( exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[SORT_MODE] ?: Sort.ACCURACY.value
            }
    }

    private object PreferenceKeys {
        val SORT_MODE = stringPreferencesKey("sort_mode")
    }

}