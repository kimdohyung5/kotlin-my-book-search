package com.kimdo.mybooksearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimdo.mybooksearchapp.R
import com.kimdo.mybooksearchapp.databinding.FragmentSearchBinding
import com.kimdo.mybooksearchapp.ui.adapter.BookSearchAdapter
import com.kimdo.mybooksearchapp.ui.adapter.BookSearchLoadStateAdapter
import com.kimdo.mybooksearchapp.ui.adapter.BookSearchPagingAdapter
import com.kimdo.mybooksearchapp.ui.viewmodel.BookSearchViewModel
import com.kimdo.mybooksearchapp.util.Constants.SEARCH_BOOKS_TIME_DELAY
import com.kimdo.mybooksearchapp.util.collectLatestStateFlow


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookSearchViewModel: BookSearchViewModel
    //private lateinit var bookSearchAdapter: BookSearchAdapter
    private lateinit var bookSearchAdapter: BookSearchPagingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        setupRecyclerView()
        searchBooks()
        setupLoadState()

        collectLatestStateFlow(bookSearchViewModel.searchPagingResult) {
            bookSearchAdapter.submitData(it )
        }

//        bookSearchViewModel.searchResult.observe(viewLifecycleOwner) { response ->
//            val books = response.documents
//            bookSearchAdapter.submitList(books )
//        }
    }

    private fun setupRecyclerView() {
//        bookSearchAdapter = BookSearchAdapter()
        bookSearchAdapter = BookSearchPagingAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration( requireContext(), LinearLayoutManager.VERTICAL))
//            adapter = bookSearchAdapter

            adapter = bookSearchAdapter.withLoadStateFooter(
                footer = BookSearchLoadStateAdapter(bookSearchAdapter::retry)
            )
        }

        bookSearchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToFragmentBook(it)
            findNavController().navigate( action )
        }
    }

    private fun searchBooks() {
        var startTime = System.currentTimeMillis()
        var endTime:Long

        binding.etSearch.text = Editable.Factory.getInstance().newEditable(bookSearchViewModel.query)

        binding.etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if( endTime - startTime >= SEARCH_BOOKS_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    if(query.isNotEmpty()) {
                        Log.i("bbbb", "query=${query}")
//                        bookSearchViewModel.searchBooks(query)
                        bookSearchViewModel.searchBooksPaging(query)
                        bookSearchViewModel.query = query
                    }
                }
            }
            startTime = endTime
        }
    }

    private fun setupLoadState() {
        bookSearchAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isListEmpty = bookSearchAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached



            binding.tvEmptylist.isVisible = isListEmpty
            binding.rvSearchResult.isVisible = !isListEmpty

            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading

//            binding.btnRetry.isVisible = loadState.refresh is LoadState.Error
//                    || loadState.append is LoadState.Error
//                    || loadState.prepend is LoadState.Error
//            val errorState: LoadState.Error? = loadState.append as? LoadState.Error
//                ?: loadState.prepend as? LoadState.Error
//                ?: loadState.refresh as? LoadState.Error
//            errorState?.let {
//                Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
//            }
        }

//        binding.btnRetry.setOnClickListener {
//            bookSearchAdapter.retry()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}