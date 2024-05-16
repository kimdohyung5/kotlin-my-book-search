package com.kimdo.mybooksearchapp.util

import android.content.Context
import com.kimdo.mybooksearchapp.BuildConfig
import kotlin.concurrent.Volatile

object Constants {
    const val BASE_URL = "https://dapi.kakao.com/"
    const val API_KEY = BuildConfig.bookApiKey
    const val SEARCH_BOOKS_TIME_DELAY= 100L
    const val DATASTORE_NAME =  "preferences_datastore"
    const val PAGING_SIZE = 15
}


// singleton 예제..

//class DBHelper private constructor(context: Context) {
//    companion object {
//        @Volatile
//        private var instance: DBHelper? = null
//        fun getInstance(context: Context): DBHelper = instance ?:
//            synchronized(DBHelper::class.java) {
//                instance ?: DBHelper(context).also {
//                    instance = it
//                }
//            }
//    }
//}