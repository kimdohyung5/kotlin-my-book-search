package com.kimdo.mybooksearchapp.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kimdo.mybooksearchapp.ui.view.FavoriteFragment
import com.kimdo.mybooksearchapp.ui.view.SearchFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> FavoriteFragment.collectLatestStateFlow(flow: Flow<T>, collect: suspend (T) -> Unit ) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun <T> SearchFragment.collectLatestStateFlow(flow: Flow<T>, collect: suspend (T) -> Unit ) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}