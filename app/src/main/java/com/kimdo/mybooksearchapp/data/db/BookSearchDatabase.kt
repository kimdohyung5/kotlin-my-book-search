package com.kimdo.mybooksearchapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.kimdo.mybooksearchapp.data.model.Book

@Database( entities = [ Book::class ], version = 1, exportSchema = false)
@TypeConverters( OrmConverter::class )
abstract class BookSearchDatabase : RoomDatabase(){
    abstract fun bookSearchDao(): BookSearchDao

    companion object {
        @Volatile
        private var instance:BookSearchDatabase? = null

        private fun buildDatabase(context: Context) : BookSearchDatabase  =
            Room.databaseBuilder( context.applicationContext, BookSearchDatabase::class.java, "favorite-books").build()

        fun getInstance(context: Context ) = instance ?: synchronized(BookSearchDatabase::class.java) {
            instance ?: buildDatabase(context ).also {
                instance = it
            }
        }
    }
}