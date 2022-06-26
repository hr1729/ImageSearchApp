package com.codinginflow.imagesearchapp.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn

class viewModel @ViewModelInject constructor(private val repo:repository,@Assisted state:SavedStateHandle
):ViewModel() {
    private val currentQuery = state.getLiveData(CURRENT_QUERY,DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repo.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        private val CURRENT_QUERY="current_query"
        private const val DEFAULT_QUERY = "cats"
    }
}